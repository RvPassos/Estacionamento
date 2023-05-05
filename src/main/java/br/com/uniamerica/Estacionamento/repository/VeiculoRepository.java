package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Condutor;
import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    @Query("SELECT m FROM Movimentacao m WHERE m.veiculoId = :veiculo AND m.ativo = true")
    List<Movimentacao> findVeiculoAtivoMovimentacao(@Param("veiculo") final Veiculo veiculo);

    @Query("SELECT v FROM Veiculo v WHERE v.ativo = true ")
    List<Condutor> findAtivo();
}
