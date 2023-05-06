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
    @Query("FROM Movimentacao WHERE condutorId = :condutor AND ativo = true")
    List<Movimentacao> findCondutorAtivoMovimentacao(@Param("condutor") final Condutor condutor);

    @Query("FROM Condutor WHERE ativo = true ")
    List<Condutor> findAtivo();

    @Query("FROM Condutor WHERE id = :id")
    List<Condutor> findIdExistente(@Param("id")final Long id);

    @Query("FROM Condutor WHERE cpf = :cpf")
    List<Condutor> getByCpf (@Param("cpf") final String cpf);

    @Query("FROM Condutor WHERE telefone = :telefone")
    List<Condutor> getByTelefone (@Param("telefone") final String telefone);

    @Query("FROM Condutor WHERE cpf = :cpf AND id != :id")
    List<Condutor> getByCpfPut (@Param("cpf") final String cpf, @Param("id") final Long id);

    @Query("FROM Condutor WHERE telefone = :telefone AND id != :id")
    List<Condutor> getByTelefonePut (@Param("telefone") final String telefone, @Param("id") final Long id);

}
