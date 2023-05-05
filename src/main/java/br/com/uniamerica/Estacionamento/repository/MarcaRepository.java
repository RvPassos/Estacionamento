package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.entity.Marca;
import br.com.uniamerica.Estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    @Query("SELECT ma FROM Marca ma WHERE ma.ativo = true")
    List<Marca> findByAtivo();

    @Query("SELECT mo FROM Modelo mo WHERE mo.marcaId = :marca AND mo.ativo = true")
    List<Modelo> findMarcaAtivoModelo(@Param("marca") final Marca marca);

}
