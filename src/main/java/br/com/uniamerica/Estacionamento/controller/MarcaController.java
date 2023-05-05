package br.com.uniamerica.Estacionamento.controller;

import br.com.uniamerica.Estacionamento.entity.Marca;
import br.com.uniamerica.Estacionamento.entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping( "/api/marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.marcaRepository.findByAtivo());
    }
    
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try {
            this.marcaRepository.save(marca);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        this.marcaRepository.save(marca);
        return ResponseEntity.ok("Registrado cadastrado com Sucesso");
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Marca marca
    ){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if (marcaBanco == null || !marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Não foi possivel idenficar o registro no banco");
            }
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Registro atualizacao com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam ("id") final Long id){

        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        if (marca == null){
            return ResponseEntity.badRequest().body("Condutor não encontrado");
        }
        List<Modelo> marcaAtivo = this.marcaRepository.findMarcaAtivoModelo(marca);
        if (!marcaAtivo.isEmpty()){
            if (marca.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("Já está inativo");
            }
            else{
                try {
                    marca.setAtivo(Boolean.FALSE);
                    this.marcaRepository.save(marca);
                    return ResponseEntity.ok("Marca esta inativa");
                }
                catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try {
            this.marcaRepository.delete(marca);
            return ResponseEntity.ok("Marca deletada");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }
}
