package com.jennyfersampaio.icompras.pedidos.validator;

import com.jennyfersampaio.icompras.pedidos.client.ClientesClient;
import com.jennyfersampaio.icompras.pedidos.client.ProdutosClient;
import com.jennyfersampaio.icompras.pedidos.client.representation.ClientesRepresentation;
import com.jennyfersampaio.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.jennyfersampaio.icompras.pedidos.model.ItemPedido;
import com.jennyfersampaio.icompras.pedidos.model.Pedido;
import com.jennyfersampaio.icompras.pedidos.model.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);

//        Exemplo de código
//        List<Long> codigosProdutos =
//                pedido.getItens()
//                        .stream().map(i -> i.getCodigoProduto()).toList();
//        codigosProdutos.forEach(codigoProdutos -> {
//            ResponseEntity<ProdutoRepresentation> response = produtosClient.obterDados(codigoProdutos);
//            ProdutoRepresentation produto = response.getBody();
//        });
    }

    private void validarCliente(Long codigoCliente){
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClientesRepresentation cliente = response.getBody();
            log.info("Cliente de código {} encontrado: {}", cliente.codigo(), cliente.nome());
        } catch (FeignException.NotFound e){
            var message = String.format("CLiente de codigo %d não encontrado.", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item){
        try{
            var response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto de código {} encontrado: {}", produto.codigo(), produto.nome());
        } catch (FeignException.NotFound e){
            var message = String.format("Produto de codigo %d não encontrado.", item.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }
}
