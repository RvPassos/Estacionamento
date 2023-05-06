package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){

        Assert.isTrue(movimentacao.getVeiculoId() != null, "Veiculo não encontrado");

        Assert.isTrue(movimentacao.getCondutorId() != null, "Condutor não encontrado");

        Assert.isTrue(movimentacao.getEntrada() != null, "Entrada está invalida");

        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Movimentacao movimentacao, final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

        Assert.isTrue(movimentacaoBanco != null || movimentacaoBanco.getId().equals(movimentacao.getId()), "Não foi possivel identificar o registro informado.");

        Assert.isTrue(movimentacao.getVeiculoId() != null, "Veiculo não encontrado");

        Assert.isTrue(movimentacao.getCondutorId() != null, "Condutor não encontrado");

        Assert.isTrue(movimentacao.getEntrada() != null, "Entrada está invalida");

    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);

        movimentacaoBanco.setAtivo(Boolean.FALSE);
        this.movimentacaoRepository.save(movimentacao);
    }
}
