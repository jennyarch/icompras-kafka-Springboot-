package com.jennyfersampaio.icompras.pedidos.controller.dto;

import com.jennyfersampaio.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento){
}
