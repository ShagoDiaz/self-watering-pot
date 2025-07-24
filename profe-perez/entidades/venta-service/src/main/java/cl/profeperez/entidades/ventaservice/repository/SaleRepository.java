package cl.profeperez.entidades.ventaservice.repository;

import cl.profeperez.entidades.ventaservice.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
