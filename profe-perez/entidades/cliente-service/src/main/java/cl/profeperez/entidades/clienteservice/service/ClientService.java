package cl.profeperez.entidades.clienteservice.service;

import cl.profeperez.entidades.clienteservice.model.Client;
import cl.profeperez.entidades.clienteservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing clients.
 * Provides CRUD operations and business logic.
 */
@Service
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Retrieve all clients.
     * @return list of clients
     */
    public List<Client> findAll() {
        log.info("Fetching all clients");
        return clientRepository.findAll();
    }

    /**
     * Find client by ID.
     * @param id client ID
     * @return optional client
     */
    public Optional<Client> findById(Long id) {
        log.info("Fetching client with id {}", id);
        return clientRepository.findById(id);
    }

    /**
     * Save or update a client.
     * @param client client to save
     * @return saved client
     */
    public Client save(Client client) {
        log.info("Saving client: {}", client);
        return clientRepository.save(client);
    }

    /**
     * Delete client by ID.
     * @param id client ID
     */
    public void deleteById(Long id) {
        log.info("Deleting client with id {}", id);
        clientRepository.deleteById(id);
    }
}
