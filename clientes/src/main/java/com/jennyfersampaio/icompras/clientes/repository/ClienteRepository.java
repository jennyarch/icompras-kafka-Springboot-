package com.jennyfersampaio.icompras.clientes.repository;

import com.jennyfersampaio.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
