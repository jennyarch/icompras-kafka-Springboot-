package com.jennyfersampaio.icompras.pedidos.service;

import com.jennyfersampaio.icompras.pedidos.client.ServicoBancarioClient;
import com.jennyfersampaio.icompras.pedidos.model.Pedido;
import com.jennyfersampaio.icompras.pedidos.repository.ItemRepository;
import com.jennyfersampaio.icompras.pedidos.repository.PedidoRepository;
import com.jennyfersampaio.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient serviceBancarioClient;

    @Transactional
    public Pedido criarPedido(Pedido pedido){
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = serviceBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

//    public void deletarPedido(Long codigo){
//        System.out.println(codigo);
//        var pedido = repository.findById(codigo)
//                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + codigo));
//        repository.delete(pedido);
//    }
}
