package io.github.lucasmatos.api.controller;


import io.github.lucasmatos.api.dto.AtualizacaoStatusPedidoDTO;
import io.github.lucasmatos.api.dto.InformacaoItemPedidoDTO;
import io.github.lucasmatos.api.dto.InformacoesPedidoDTO;
import io.github.lucasmatos.api.dto.PedidoDTO;
import io.github.lucasmatos.domain.entity.ItemPedido;
import io.github.lucasmatos.domain.entity.Pedido;
import io.github.lucasmatos.domain.enums.StatusPedido;
import io.github.lucasmatos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO dto, @PathVariable Integer id) {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .total(pedido.getTotal())
                .nomeCliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(item -> InformacaoItemPedidoDTO.builder()
                .descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());
    }
}
