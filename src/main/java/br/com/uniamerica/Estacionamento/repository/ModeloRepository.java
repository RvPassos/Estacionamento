package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Modelo;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    @Query("SELECT mo FROM Modelo mo WHERE mo.ativo = true")
    List<Modelo> findByAtivo();

    @Query("SELECT v FROM Veiculo v WHERE v.modeloId = :modelo AND v.ativo = true")
    List<Veiculo> findModeloAtivoVeiculo(@Param("modelo") final Modelo modelo);
}
