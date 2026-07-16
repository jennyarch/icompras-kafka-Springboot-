package com.jennyfersampaio.icompras.pedidos.repository;

import com.jennyfersampaio.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPedido, Long> {
}
