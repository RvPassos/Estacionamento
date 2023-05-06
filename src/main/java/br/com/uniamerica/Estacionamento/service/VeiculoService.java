package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        Assert.isTrue(veiculo.getPlaca() != null, "Placa não encontrada");

        String regexPlacaAntiga = "^[A-Z]{3}-\\d{4}$";

        String regexPlacaNova = "^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$";

        Assert.isTrue(veiculo.getPlaca().matches(regexPlacaAntiga) || veiculo.getPlaca().matches(regexPlacaNova), "Placa está errada");

        Assert.isTrue(veiculo.getModeloId() != null, "Modelo não encontrado");

        Assert.isTrue(veiculo.getCor() != null, "Cor não encontrado");

        Assert.isTrue(veiculo.getTipo() != null, "Tipo não encontrado");

        Assert.isTrue(veiculo.getAno() != null, "ano não encontrado");

        Assert.isTrue(this.veiculoRepository.findByPlaca(veiculo.getPlaca()).isEmpty(), "a placa ja existe");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Veiculo veiculo, final Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);

        Assert.isTrue(veiculoBanco != null || veiculoBanco.getId().equals(veiculo.getId()), "Não foi possivel identificar o registro no banco");

        String regexPlacaAntiga = "^[A-Z]{3}-\\d{4}$";

        String regexPlacaNova = "^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$";

        Assert.isTrue(veiculo.getPlaca().matches(regexPlacaAntiga) || veiculo.getPlaca().matches(regexPlacaNova), "Placa está errada");

        Assert.isTrue(veiculo.getPlaca() != null, "Placa não encontrada");

        Assert.isTrue(veiculo.getModeloId() != null, "Modelo não encontrado");

        Assert.isTrue(veiculo.getCor() != null, "Cor não encontrado");

        Assert.isTrue(veiculo.getTipo() != null, "Tipo não encontrado");

        Assert.isTrue(veiculo.getAno() != null, "ano não encontrado");

        Assert.isTrue(this.veiculoRepository.findByPlacaPut(veiculo.getPlaca(), id).isEmpty(), "a placa ja existe");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor =  Exception.class)
    public void deletar(final Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(veiculo.getId()).orElse(null);

        List<Movimentacao> veiculoAtivo = this.veiculoRepository.findVeiculoAtivoMovimentacao(veiculoBanco);

        if(veiculoAtivo.isEmpty()){
            this.veiculoRepository.delete(veiculoBanco);
        } else{
            veiculoBanco.setAtivo(Boolean.FALSE);
            this.veiculoRepository.save(veiculo);
        }
    }
}
