package com.jennyfersampaio.icompras.pedidos.controller;

import com.jennyfersampaio.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.jennyfersampaio.icompras.pedidos.controller.mappers.PedidoMapper;
import com.jennyfersampaio.icompras.pedidos.model.ErrorResposta;
import com.jennyfersampaio.icompras.pedidos.model.exception.ValidationException;
import com.jennyfersampaio.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto){
        try{
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e){
           var erro = new ErrorResposta("Erro validação: ", e.getField(), e.getMessage());
           return ResponseEntity.badRequest().body(erro);
        }
    }

//    @DeleteMapping("/{codigo}")
//    public ResponseEntity<Void> deletar(@PathVariable Long codigo){
//        service.deletarPedido(codigo);
//        return ResponseEntity.noContent().build();
//    }
}
