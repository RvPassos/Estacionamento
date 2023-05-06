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
    @Query("FROM Marca WHERE ativo = true")
    List<Marca> findByAtivo();

    @Query("FROM Modelo WHERE marcaId = :marca AND ativo = true")
    List<Modelo> findMarcaAtivoModelo(@Param("marca") final Marca marca);

    @Query("FROM Modelo WHERE nome = :nome")
    List<Marca> findByNome(@Param("nome") final String nome);

    @Query("FROM Modelo WHERE nome = :nome AND id != :id")
    List<Marca> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);

}
