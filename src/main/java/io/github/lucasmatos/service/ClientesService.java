package io.github.lucasmatos.service;

import io.github.lucasmatos.model.Cliente;
import io.github.lucasmatos.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    //clean bro
    @Autowired
    private ClientesRepository repository;

    /*
    @Autowired
    public  ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }*/

    /*
    @Autowired
    public void setRepository(ClientesRepository repository) {
        this.repository = repository;
    }
    */

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente) {
        //validacoes
    }
}
