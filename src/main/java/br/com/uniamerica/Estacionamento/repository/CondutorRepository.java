package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Condutor;
import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long>{
    @Query("SELECT m FROM Movimentacao m WHERE m.condutorId = :condutor AND m.ativo = true")
    List<Movimentacao> findCondutorAtivoMovimentacao(@Param("condutor") final Condutor condutor);

    @Query("SELECT c FROM Condutor c WHERE c.ativo = true ")
    List<Condutor> findAtivo();

    @Query("SELECT c FROM Condutor c WHERE c.id = :id")
    List<Condutor> findIdExistente(@Param("id")final Long id);

    @Query("SELECT c.cpf FROM Condutor c WHERE c.cpf = :cpf")
    List<Condutor> getByCpf (@Param("cpf") final String cpf);
}
