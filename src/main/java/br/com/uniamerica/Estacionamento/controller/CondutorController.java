package br.com.uniamerica.Estacionamento.controller;


import br.com.uniamerica.Estacionamento.entity.Condutor;
import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import br.com.uniamerica.Estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private CondutorService condutorService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.condutorRepository.findAtivo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try {
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Registrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Condutor condutor
    ){
        try {
            this.condutorService.editar(condutor, id);
            return ResponseEntity.ok("Registro atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam ("id") final Long id) {

        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        if (condutor == null){
            return ResponseEntity.badRequest().body("Condutor nao encontrado");
        }
        List<Movimentacao> condutorAtivo = this.condutorRepository.findCondutorAtivoMovimentacao(condutor);
        if (!condutorAtivo.isEmpty()){
            if (condutor.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("Já está inativo");
            }
            else {
                try {
                    condutor.setAtivo(Boolean.FALSE);
                    this.condutorRepository.save(condutor);
                    return ResponseEntity.ok("Condutor está inativo");
                }
                catch (DataIntegrityViolationException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try {
            this.condutorRepository.delete(condutor);
            return ResponseEntity.ok("Condutor deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}


