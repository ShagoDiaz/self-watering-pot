package cl.profeperez.entidades.clienteservice.repository;

import cl.profeperez.entidades.clienteservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Client entity.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
