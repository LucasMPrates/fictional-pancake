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
            repository.save(new Cliente("Lucas"));
            repository.save(new Cliente("Outro Cliente"));

            List<Cliente> result = repository.encontrarPorNome("Lucas");

            result.forEach(cliente -> System.out.println(cliente.getNome()));

//            System.out.println("Atualizando clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado.");
//                repository.save(c);
//            });

//            todosClientes = repository.findAll();
//            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes");
            repository.findByNomeLike("%Cli%").forEach(System.out::println);

            System.out.println("deletando clientes");
            repository.findAll().forEach(c -> {
                repository.deleteById(c.getId());
            });

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
