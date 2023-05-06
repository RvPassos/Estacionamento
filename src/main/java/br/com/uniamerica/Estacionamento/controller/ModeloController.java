package br.com.uniamerica.Estacionamento.controller;

import br.com.uniamerica.Estacionamento.entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import br.com.uniamerica.Estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(modelo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }


    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo() {
        return ResponseEntity.ok(this.modeloRepository.findByAtivo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try{
            this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro Cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Modelo modelo
    ){
        try {
            this.modeloService.editar(modelo, id);
            return ResponseEntity.ok("Registro Atualizado com Sucesso");
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
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        this.modeloService.deletar(modeloBanco);

        return ResponseEntity.ok("Modelo deletado com sucesso");
    }
}

