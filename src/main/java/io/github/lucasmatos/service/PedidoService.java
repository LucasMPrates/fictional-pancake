package io.github.lucasmatos.service;

import io.github.lucasmatos.api.dto.PedidoDTO;
import io.github.lucasmatos.domain.entity.Pedido;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}
