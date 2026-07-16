package com.jennyfersampaio.icompras.pedidos.model;

import com.jennyfersampaio.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
