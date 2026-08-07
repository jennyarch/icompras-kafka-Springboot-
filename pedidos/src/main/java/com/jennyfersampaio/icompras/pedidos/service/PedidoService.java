package com.jennyfersampaio.icompras.pedidos.service;

import com.jennyfersampaio.icompras.pedidos.client.ServicoBancarioClient;
import com.jennyfersampaio.icompras.pedidos.model.DadosPagamento;
import com.jennyfersampaio.icompras.pedidos.model.Pedido;
import com.jennyfersampaio.icompras.pedidos.model.enums.StatusPedido;
import com.jennyfersampaio.icompras.pedidos.model.enums.TipoPagamento;
import com.jennyfersampaio.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import com.jennyfersampaio.icompras.pedidos.repository.ItemRepository;
import com.jennyfersampaio.icompras.pedidos.repository.PedidoRepository;
import com.jennyfersampaio.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public void atualizarStatusPagamento(
            Long codigoPedido,
            String chavePagamento,
            boolean sucesso,
            String observacoes
    ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()){
            var msg = String.format("Pedido não encontrado para o código %d e chave pgmto %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        var pedido = pedidoEncontrado.get();
        if(sucesso){
            pedido.setStatus(StatusPedido.PAGO);
        } else{
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo){
        var pedidoEncontrado = repository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()){
            throw  new ItemNaoEncontradoException("Pedido não encontrado para o código informado.");
        }

        var pedido = pedidoEncontrado.get();
        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o processamento");

        String novaChavePagamento = serviceBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

//        Pode ser opcional, essa entrada, pois o Transactional já verifica a alteração e de forma nativa realiza caso tenha.
        repository.save(pedido);
    }

//    public void deletarPedido(Long codigo){
//        System.out.println(codigo);
//        var pedido = repository.findById(codigo)
//                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + codigo));
//        repository.delete(pedido);
//    }
}
