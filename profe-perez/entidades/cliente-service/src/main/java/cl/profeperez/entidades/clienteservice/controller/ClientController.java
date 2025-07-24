package cl.profeperez.entidades.clienteservice.controller;

import cl.profeperez.entidades.clienteservice.model.Client;
import cl.profeperez.entidades.clienteservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing clients.
 */
@RestController
@RequestMapping("/cliente")
@Slf4j
@Tag(name = "Client Controller", description = "CRUD operations for clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Get all clients.
     * @return list of clients
     */
    @Operation(summary = "Get all clients")
    @GetMapping
    public List<Client> getAll() {
        log.info("Getting all clients");
        return clientService.findAll();
    }

    /**
     * Get client by ID.
     * @param id client ID
     * @return client or 404
     */
    @Operation(summary = "Get client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        log.info("Getting client with id {}", id);
        return clientService.findById(id)
                .map(client -> {
                    log.info("Client found: {}", client);
                    return ResponseEntity.ok(client);
                })
                .orElseGet(() -> {
                    log.warn("Client with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Create a new client.
     * @param client client to create
     * @return created client
     */
    @Operation(summary = "Create a new client")
    @PostMapping
    public Client create(@RequestBody Client client) {
        log.info("Creating client: {}", client);
        return clientService.save(client);
    }

    /**
     * Update an existing client.
     * @param id client ID
     * @param client client data to update
     * @return updated client or 404
     */
    @Operation(summary = "Update an existing client")
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        log.info("Updating client with id {}", id);
        return clientService.findById(id)
                .map(existing -> {
                    existing.setName(client.getName());
                    existing.setEmail(client.getEmail());
                    existing.setPhone(client.getPhone());
                    existing.setAddress(client.getAddress());
                    clientService.save(existing);
                    log.info("Client updated: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    log.warn("Client with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete client by ID.
     * @param id client ID
     * @return 204 or 404
     */
    @Operation(summary = "Delete client by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting client with id {}", id);
        if (clientService.findById(id).isPresent()) {
            clientService.deleteById(id);
            log.info("Client with id {} deleted", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Client with id {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }
}
