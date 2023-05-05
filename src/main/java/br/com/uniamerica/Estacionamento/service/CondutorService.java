package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Condutor;
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

        List<Condutor> bruh = this.condutorRepository.getByCpf(condutor.getCpf());

        Assert.isTrue(bruh.isEmpty(), "Cpf ja tem pai");

        Assert.isTrue(condutor.getNome() != null, "Nome está nulo");

        String padrao = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";

        Assert.isTrue(condutor.getTelefone().matches(padrao), "formato do telefone invalido");

        Assert.isTrue(condutor.getTelefone() != null, "Telefone está nulo");

        Assert.isTrue(condutor.getTempoPago() != null, "Tempo pago invalido");

        Assert.isTrue(condutor.getTempoDesconto() != null, "Tempo desconto invalido");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Condutor condutor, final Long id){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

        Assert.isTrue(condutorBanco != null || !condutorBanco.getId().equals(condutor.getId()), "Não foi possivel identificar o registro no banco");

        List<Condutor> contemcpf = this.condutorRepository.getByCpf(condutor.getCpf());

        String regexCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";

        Assert.isTrue(condutor.getCpf().matches(regexCpf), "Cpf invalido");

        String padrao = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";

        Assert.isTrue(condutor.getTelefone().matches(padrao), "formato do telefone invalido");

        Assert.isTrue(contemcpf.isEmpty(), "Cpf ja existe");

        Assert.isTrue(!this.condutorRepository.findIdExistente(condutor.getId()).equals(condutor.getAtivo()), "Id já existente" );


        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(){

    }

}
