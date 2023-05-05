package br.com.uniamerica.Estacionamento.controller;


import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public ResponseEntity<?> getById(@RequestParam("id") final Long id){
        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        return veiculo == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> getByAtivo(){
        return ResponseEntity.ok(this.veiculoRepository.findAtivo());
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try {
            this.veiculoRepository.save(veiculo);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        this.veiculoRepository.save(veiculo);
        return ResponseEntity.ok("Registrado com Sucesso");
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Veiculo veiculo
    ){
        try{
            final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

            if (veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId())){
                throw new RuntimeException("Não foi possivel idenficar o registro no banco");
            }
            this.veiculoRepository.save(veiculo);
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
    public ResponseEntity<?> deletar (@RequestParam ("id") final Long id) {

        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        if (veiculo == null){
            return ResponseEntity.badRequest().body("Condutor nao encontrado");
        }
        List<Movimentacao> condutorAtivo = this.veiculoRepository.findVeiculoAtivoMovimentacao(veiculo);
        if (!condutorAtivo.isEmpty()){
            if (veiculo.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("Já está inativo");
            }
            else {
                try {
                    veiculo.setAtivo(Boolean.FALSE);
                    this.veiculoRepository.save(veiculo);
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
            this.veiculoRepository.delete(veiculo);
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
