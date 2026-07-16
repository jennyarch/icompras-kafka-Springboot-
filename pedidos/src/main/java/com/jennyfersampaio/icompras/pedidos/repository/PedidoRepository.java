package com.jennyfersampaio.icompras.pedidos.repository;

import com.jennyfersampaio.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
