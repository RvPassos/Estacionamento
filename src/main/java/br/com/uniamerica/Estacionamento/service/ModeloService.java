package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.entity.Modelo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeloService {


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){

    }

}
