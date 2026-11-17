-- ========================================================
-- Script DDL: Sistema de Assistência Técnica (schema.sql)
-- ========================================================

DROP TABLE IF EXISTS agendamento;
DROP TABLE IF EXISTS equipamento;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS usuario;

-- 1. Tabela de Usuários (Operadores do Sistema)
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    ultimo_acesso TIMESTAMP
);

-- 2. Tabela de Clientes (CPF armazenado criptografado - RN03)
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    telefone VARCHAR(30),
    email VARCHAR(100),
    endereco VARCHAR(255)
);

-- 3. Tabela de Equipamentos (Obrigatoriamente vinculado a um cliente - RN07)
CREATE TABLE equipamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    numero_serie VARCHAR(100),
    defeito_declarado TEXT,
    CONSTRAINT fk_equipamento_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

-- 4. Tabela de Agendamentos (Ordenados por data - RN06)
CREATE TABLE agendamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    equipamento_id BIGINT NOT NULL,
    data_agendamento TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacoes TEXT,
    CONSTRAINT fk_agendamento_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_agendamento_equipamento FOREIGN KEY (equipamento_id) REFERENCES equipamento(id) ON DELETE CASCADE
);
