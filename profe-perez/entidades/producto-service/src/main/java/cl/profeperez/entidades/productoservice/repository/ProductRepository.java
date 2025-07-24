package cl.profeperez.entidades.productoservice.repository;

import cl.profeperez.entidades.productoservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
