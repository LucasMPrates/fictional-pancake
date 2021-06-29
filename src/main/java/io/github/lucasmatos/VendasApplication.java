package io.github.lucasmatos;

import io.github.lucasmatos.domain.entity.Cliente;
import io.github.lucasmatos.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository repository) {
        return args -> {
            System.out.println("Salvando clientes");
            repository.salvar(new Cliente("Dougllas"));
            repository.salvar(new Cliente("Outro Cliente"));

            List<Cliente> todosClientes = repository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                repository.atualizar(c);
            });

            todosClientes = repository.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes");
            repository.buscarPorNome("Cli").forEach(System.out::println);

            System.out.println("deletando clientes");
            repository.obterTodos().forEach(c -> {
                repository.delete(c.getId());
            });

            todosClientes = repository.obterTodos();
            if (todosClientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
