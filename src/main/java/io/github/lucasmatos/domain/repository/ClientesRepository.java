package io.github.lucasmatos.domain.repository;

import io.github.lucasmatos.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    //funciona por uma convencao da assinatura
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);
}
