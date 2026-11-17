package com.assistencia.repository;

import com.assistencia.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // RN05: Busca de clientes por termo (nome, telefone ou email)
    @Query("SELECT c FROM Cliente c WHERE " +
           "LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "(c.email IS NOT NULL AND LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))) OR " +
           "(c.telefone IS NOT NULL AND c.telefone LIKE CONCAT('%', :termo, '%'))")
    List<Cliente> buscarPorTermo(@Param("termo") String termo);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
