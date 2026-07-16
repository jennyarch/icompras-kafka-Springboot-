package com.jennyfersampaio.icompras.pedidos.controller.dto;

import com.jennyfersampaio.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}
