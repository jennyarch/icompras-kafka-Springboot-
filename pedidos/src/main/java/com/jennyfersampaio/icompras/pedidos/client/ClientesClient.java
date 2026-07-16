package com.jennyfersampaio.icompras.pedidos.client;

import com.jennyfersampaio.icompras.pedidos.client.representation.ClientesRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${icompras.pedidos.clients.clientes.url}")
public interface ClientesClient {

    @GetMapping("{codigo}")
    ResponseEntity<ClientesRepresentation> obterDados(@PathVariable("codigo") Long codigo);
}
