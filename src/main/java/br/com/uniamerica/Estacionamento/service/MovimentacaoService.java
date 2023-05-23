package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Recibo;
import br.com.uniamerica.Estacionamento.entity.Configuracao;
import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

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

        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public Recibo saida(final Long id){

        Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        movimentacao.setSaida(LocalDateTime.now());

        Long tempoTotal = movimentacao.getEntrada().until(movimentacao.getSaida(), ChronoUnit.HOURS);

        movimentacao.setTempo(tempoTotal);

        Configuracao configuracao = this.configuracaoRepository.findById(1L).orElse(null);

        BigDecimal horas = new BigDecimal(movimentacao.getTempo());

        BigDecimal valorTotal = configuracao.getValorHora().multiply(horas);

        movimentacao.setValorTotal(valorTotal);

        Long desconto = movimentacao.getTempo() / configuracao.getTempoParaDesconto();

        movimentacao.setValorDesconto(desconto);

        System.out.println(desconto);

        BigDecimal calculo = new BigDecimal(desconto).multiply(configuracao.getTempoDeDesconto());

        BigDecimal total = movimentacao.getValorTotal().subtract(calculo);

        movimentacao.setValorTotal(total);

        this.movimentacaoRepository.save(movimentacao);

        return new Recibo(movimentacao.getEntrada(), movimentacao.getSaida(), movimentacao.getCondutorId(), movimentacao.getVeiculoId(), movimentacao.getTempo(), configuracao.getTempoParaDesconto(), movimentacao.getValorTotal(), movimentacao.getValorDesconto());
    }


    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);

        movimentacaoBanco.setAtivo(Boolean.FALSE);
        this.movimentacaoRepository.save(movimentacao);
    }
}
