package io.github.lucasmatos.service;

import io.github.lucasmatos.api.dto.PedidoDTO;
import io.github.lucasmatos.domain.entity.Pedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
