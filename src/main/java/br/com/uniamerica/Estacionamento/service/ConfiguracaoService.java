package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Configuracao configuracao){

        Assert.isTrue(configuracao.getValorHora() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getValorMinutoMulta() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getInicioExpediente() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getFimExpediente() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getTempoParaDesconto() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getTempoDeDesconto() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getGerarDesconto() != null, "Nao pode ser nulo");

        this.configuracaoRepository.save(configuracao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Configuracao configuracao, final Long id){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);

        Assert.isTrue(configuracaoBanco != null || configuracaoBanco.getId().equals(configuracao.getId()), "Nao foi possivel identificar o registro no banco");

        Assert.isTrue(configuracao.getValorHora() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getValorMinutoMulta() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getInicioExpediente() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getFimExpediente() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getTempoParaDesconto() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getTempoDeDesconto() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getGerarDesconto() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getVagasCarro() != null, "Nao pode ser nulo");

        Assert.isTrue(configuracao.getVagasVan() != null, "Nao pode ser nulo");
    }


}
