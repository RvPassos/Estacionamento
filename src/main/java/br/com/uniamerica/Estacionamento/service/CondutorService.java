package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Condutor;
import br.com.uniamerica.Estacionamento.entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor){

        Assert.isTrue(condutor.getCpf() != null,"O cpf está nulo");

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";

        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Cpf invalido");

        List<Condutor> contemcpf = this.condutorRepository.getByCpf(condutor.getCpf());

        Assert.isTrue(contemcpf.isEmpty(), "Cpf ja existe");

        Assert.isTrue(condutor.getNome() != null, "Nome está nulo");

        String padrao = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";

        Assert.isTrue(condutor.getTelefone().matches(padrao), "formato do telefone invalido");

        Assert.isTrue(condutor.getTelefone() != null, "Telefone está nulo");

        Assert.isTrue(condutor.getTempoPago() != null, "Tempo pago invalido");

        Assert.isTrue(condutor.getTempoDesconto() != null, "Tempo desconto invalido");

        Assert.isTrue(this.condutorRepository.getByTelefone(condutor.getTelefone()).isEmpty(), "o telefone ja existe");

        Assert.isTrue(this.condutorRepository.getByCpf(condutor.getCpf()).isEmpty(), "O cpf ja existe");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Condutor condutor, final Long id){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

        Assert.isTrue(condutor.getCpf() != null,"O cpf está nulo");

        Assert.isTrue(condutor.getNome() != null, "Nome está nulo");

        Assert.isTrue(condutor.getTelefone() != null, "Telefone está nulo");

        Assert.isTrue(condutor.getTempoPago() != null, "Tempo pago está nulo");

        Assert.isTrue(condutor.getTempoDesconto() != null, "Tempo desconto invalido");

        Assert.isTrue(this.condutorRepository.getByCpfPut(condutor.getCpf(), id).isEmpty(), "O cpf ja existe");

        Assert.isTrue(this.condutorRepository.getByTelefonePut(condutor.getTelefone(), id).isEmpty(),"o telefone ja existe");

        Assert.isTrue(condutorBanco != null || condutorBanco.getId().equals(condutor.getId()), "Não foi possivel identificar o registro no banco");

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";

        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Cpf invalido");

        String padrao = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";

        Assert.isTrue(condutor.getTelefone().matches(padrao), "formato do telefone invalido");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(condutor.getId()).orElse(null);

        List<Movimentacao> condutorAtivo = this.condutorRepository.findCondutorAtivoMovimentacao(condutorBanco);

        if(condutorAtivo.isEmpty()){
            this.condutorRepository.delete(condutorBanco);
        } else {
            condutorBanco.setAtivo(Boolean.FALSE);
            this.condutorRepository.save(condutor);
        }

    }
}
