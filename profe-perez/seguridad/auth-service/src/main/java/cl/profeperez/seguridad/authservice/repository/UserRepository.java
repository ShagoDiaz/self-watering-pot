package cl.profeperez.seguridad.authservice.repository;

import cl.profeperez.seguridad.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
