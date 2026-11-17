package com.assistencia.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Atende à Regra de Negócio 03 (RN03):
 * O CPF dos clientes deve ser armazenado de forma criptografada no banco de dados.
 * Converte de forma transparente os dados ao persistir e ao recuperar do banco.
 */
@Converter
public class CpfCryptoConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    // Chave de 128-bit (16 bytes) para criptografia AES simétrica
    private static final byte[] SECRET_KEY = "MinhaChaveSec123".getBytes(StandardCharsets.UTF_8);

    @Override
    public String convertToDatabaseColumn(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(cpf.trim().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criptografar CPF para persistência", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(dbData.trim());
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Se o dado não estiver cifrado (por exemplo, migração legada), retorna o dado original
            return dbData;
        }
    }
}
