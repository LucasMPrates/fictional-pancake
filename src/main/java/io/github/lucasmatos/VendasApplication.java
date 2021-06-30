package io.github.lucasmatos;

import io.github.lucasmatos.domain.entity.Cliente;
import io.github.lucasmatos.domain.entity.Pedido;
import io.github.lucasmatos.domain.repository.ClientesRepository;
import io.github.lucasmatos.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository repository, @Autowired PedidoRepository pedidoRepository) {
        return args -> {
            System.out.println("Salvando clientes");
            Cliente lucas = new Cliente("Lucas");
            repository.save(lucas);

            Pedido pedidoLucas = new Pedido();
            pedidoLucas.setCliente(lucas);
            pedidoLucas.setDataPedido(LocalDate.now());
            pedidoLucas.setTotal(BigDecimal.valueOf(10));

            pedidoRepository.save(pedidoLucas);
            Cliente cliente = repository.findClienteFetchPedidos(lucas.getId());

            System.out.println("Imprimindo informacoes");
            System.out.println(cliente.toString());
            System.out.println(cliente.getPedidos());

            List<Pedido> pedidos = pedidoRepository.findByCliente(lucas);

            System.out.println("Pedidos");
            pedidos.forEach(System.out::println);


//            System.out.println("Atualizando clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado.");
//                repository.save(c);
//            });

//            todosClientes = repository.findAll();
//            todosClientes.forEach(System.out::println);

//            System.out.println("Buscando clientes");
//            repository.findByNomeLike("%Cli%").forEach(System.out::println);
//
//            System.out.println("deletando clientes");
//            repository.findAll().forEach(c -> {
//                repository.deleteById(c.getId());
//            });

//            todosClientes = repository.findAll();
//            if (todosClientes.isEmpty()) {
//                System.out.println("Nenhum cliente encontrado.");
//            } else {
//                todosClientes.forEach(System.out::println);
//            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
