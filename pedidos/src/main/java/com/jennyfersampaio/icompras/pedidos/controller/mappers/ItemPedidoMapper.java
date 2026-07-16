package com.jennyfersampaio.icompras.pedidos.controller.mappers;

import com.jennyfersampaio.icompras.pedidos.controller.dto.ItemPedidoDTO;
import com.jennyfersampaio.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
