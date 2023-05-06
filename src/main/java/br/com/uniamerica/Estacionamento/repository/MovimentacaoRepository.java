package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Query("FROM Movimentacao WHERE saida = null")
    List<Movimentacao> findByAberta();
}
