package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Modelo;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){

        Assert.isTrue(modelo.getNome() != null, "Nome invalido");

        Assert.isTrue(this.modeloRepository.findByNome(modelo.getNome()).isEmpty(), "Já existe esse modelo");

        Assert.isTrue(modelo.getMarcaId() != null, "id da marca invalido");

        Assert.isTrue(!this.marcaRepository.findById(modelo.getMarcaId().getId()).isEmpty(),"Marca não existe");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Modelo modelo, final Long id){

        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        Assert.isTrue(this.modeloRepository.findByNomePut(modelo.getNome(), id).isEmpty(), "ja existe esse modelo");

        Assert.isTrue(modeloBanco != null || modeloBanco.getId().equals(modelo.getId()), "Não foi possivel indenficar o registro no banco");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(modelo.getId()).orElse(null);

        List<Veiculo> modeloAtivo = this.modeloRepository.findModeloAtivoVeiculo(modeloBanco);

        if(modeloAtivo.isEmpty()){
            this.modeloRepository.delete(modeloBanco);
        } else{
            modeloBanco.setAtivo(Boolean.FALSE);
            this.modeloRepository.save(modelo);
        }
    }

}
