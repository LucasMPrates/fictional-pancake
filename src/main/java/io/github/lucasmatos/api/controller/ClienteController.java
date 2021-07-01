package io.github.lucasmatos.api.controller;

import io.github.lucasmatos.domain.entity.Cliente;
import io.github.lucasmatos.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClientesRepository repository;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteById = repository.findById(id);
        if (clienteById.isPresent()) {
            return ResponseEntity.ok(clienteById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente responseCliente = repository.save(cliente);
        return ResponseEntity.ok(responseCliente);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id) {

        Optional<Cliente> byId = repository.findById(id);
        if (byId.isPresent()) {
            repository.delete(byId.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        if(clienteOptional.isPresent()){
            cliente.setId(clienteOptional.get().getId());
            repository.save(cliente);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
