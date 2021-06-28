package io.github.lucasmatos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//uma maneira de mostrar ao spring o que deve ser escaneado
//por padrao é o pacote que contem a classe com SpringBootAplication io.github.lucasmatos
//@ComponentScan(basePackages = {
//                                "io.github.lucasmatos.repository",
//                                "io.github.lucasmatos.service",}
//)
@SpringBootApplication
@RestController
public class VendasApplication {

    //através de beans e arquivo configuration
    //@Autowired
    ///@Qualifier("applicationName")

    @Value("${application.name}")
    private String applicationName;

    @Cachorro
    private Animal animal;

    @Bean
    public CommandLineRunner executar() {
        return args -> {
            this.animal.fazerBarulho();
        };
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
