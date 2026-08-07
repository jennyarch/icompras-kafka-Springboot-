package com.jennyfersampaio.icompras.pedidos.controller.dto;

/**
 *
 * {
 *     "codigo": "number",
 *     "chavePagamento": "string",
 *     "status": "boolean",
 *     "observacoes": "string"
 * }
 * Headers:
 * {
 *     "apiKey": "string"
 * }
 */
public record RecebimentoCallbackPagamentoDTO(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes
) {
}
