package io.github.lucasmatos.service.impl;

import io.github.lucasmatos.api.dto.ItemPedidoDTO;
import io.github.lucasmatos.api.dto.PedidoDTO;
import io.github.lucasmatos.domain.entity.Cliente;
import io.github.lucasmatos.domain.entity.ItemPedido;
import io.github.lucasmatos.domain.entity.Pedido;
import io.github.lucasmatos.domain.entity.Produto;
import io.github.lucasmatos.domain.enums.StatusPedido;
import io.github.lucasmatos.domain.repository.ClientesRepository;
import io.github.lucasmatos.domain.repository.ItemPedidoRepository;
import io.github.lucasmatos.domain.repository.PedidoRepository;
import io.github.lucasmatos.domain.repository.ProdutosRepository;
import io.github.lucasmatos.exception.PedidoNaoEncontradoException;
import io.github.lucasmatos.exception.RegraNegocioException;
import io.github.lucasmatos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //cria construtor com os atributos final
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItemPedidoRepository itemPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items");
        }
        return items.stream().map(itemPedidoDTO -> {
            Integer idProduto = itemPedidoDTO.getProduto();
            Produto produto = produtosRepository.findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto inválido:" + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
