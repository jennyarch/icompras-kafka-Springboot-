package com.jennyfersampaio.icompras.clientes.service;

import com.jennyfersampaio.icompras.clientes.model.Cliente;
import com.jennyfersampaio.icompras.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo){
        return repository.findById(codigo);
    }

    public List<Cliente> obterTodos(Cliente cliente){
        return repository.findAll();
    }
}
