package br.com.uniamerica.Estacionamento.controller;

import br.com.uniamerica.Estacionamento.entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.Estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);

        return configuracao == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoService.cadastrar(configuracao);
            return ResponseEntity.ok("Registrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Configuracao configuracao
    ){
        try{
            this.configuracaoService.editar(configuracao, id);
            return ResponseEntity.ok("Registro atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }

    }
}
