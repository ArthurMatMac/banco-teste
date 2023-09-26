package com.db2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Date;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    @Query("SELECT t FROM Funcionario t WHERE t.data_admissao = ?1")
    Funcionario findFuncionarioByData_admissao(Date data);
}
