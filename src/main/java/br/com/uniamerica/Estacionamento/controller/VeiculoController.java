package br.com.uniamerica.Estacionamento.controller;


import br.com.uniamerica.Estacionamento.entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.Estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

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
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("Registrado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Veiculo veiculo
    ){
        try{
            this.veiculoService.editar(veiculo, id);
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
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

        this.veiculoService.deletar(veiculoBanco);

        return ResponseEntity.ok("Veiculo deletado com sucesso");
    }

}
