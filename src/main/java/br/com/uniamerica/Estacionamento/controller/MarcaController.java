package br.com.uniamerica.Estacionamento.controller;

import br.com.uniamerica.Estacionamento.entity.Marca;
import br.com.uniamerica.Estacionamento.entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import br.com.uniamerica.Estacionamento.service.MarcaService;
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

    @Autowired
    private MarcaService marcaService;

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
            this.marcaService.cadastrar(marca);
            return ResponseEntity.ok("Registrado cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Marca marca
    ){
        try{
            this.marcaService.editar(marca, id);
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

        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        this.marcaService.deletar(marcaBanco);

        return ResponseEntity.ok("Marca deletada com sucesso");
    }
}
