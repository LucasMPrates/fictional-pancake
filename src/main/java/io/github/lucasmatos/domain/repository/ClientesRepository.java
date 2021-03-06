package io.github.lucasmatos.domain.repository;

import io.github.lucasmatos.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    //funciona por uma convencao da assinatura
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    @Query(value = "select * from Cliente c where c.nome like :nome", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query("delete from Cliente c where c.nome =:nome ")
    @Modifying
    void deleteByNome(String nome);
                                                                   //opcional, como está mapeado a jpa já entende
    @Query("select c from Cliente c left join fetch c.pedidos p where c.id= :id") //on p.cliente_id = c.id")
    Cliente findClienteFetchPedidos( @Param("id") Integer id);
}
