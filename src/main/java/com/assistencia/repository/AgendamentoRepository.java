package com.assistencia.repository;

import com.assistencia.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // RN06: Listagem ordenada por data de agendamento exibindo todas as informações de cliente e equipamento
    @Query("SELECT a FROM Agendamento a " +
           "LEFT JOIN FETCH a.cliente c " +
           "LEFT JOIN FETCH a.equipamento e " +
           "ORDER BY a.dataAgendamento ASC")
    List<Agendamento> findAllOrderByDataAgendamentoAsc();
}
