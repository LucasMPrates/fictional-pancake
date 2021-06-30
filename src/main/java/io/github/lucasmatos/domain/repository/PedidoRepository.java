package io.github.lucasmatos.domain.repository;

import io.github.lucasmatos.domain.entity.Cliente;
import io.github.lucasmatos.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
