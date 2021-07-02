package io.github.lucasmatos.api.controller;


import io.github.lucasmatos.api.dto.PedidoDTO;
import io.github.lucasmatos.domain.entity.Pedido;
import io.github.lucasmatos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoSalvo = service.salvar(pedidoDTO);
        return pedidoSalvo.getId();
    }
}
