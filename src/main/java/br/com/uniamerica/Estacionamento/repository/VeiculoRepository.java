package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    @Query("FROM Movimentacao WHERE veiculoId = :veiculo AND ativo = true")
    List<Movimentacao> findVeiculoAtivoMovimentacao(@Param("veiculo") final Veiculo veiculo);

    @Query("FROM Veiculo WHERE ativo = true ")
    List<Veiculo> findAtivo();

    @Query("FROM Veiculo WHERE placa = :placa")
    List<Veiculo> findByPlaca(@Param("placa") final String placa);

    @Query("FROM Veiculo WHERE placa = :placa AND id != :id")
    List<Veiculo> findByPlacaPut(@Param("placa") final String placa, @Param("id") final Long id);
}
