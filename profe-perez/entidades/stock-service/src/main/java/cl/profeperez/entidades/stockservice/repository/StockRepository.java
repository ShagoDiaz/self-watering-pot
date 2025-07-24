package cl.profeperez.entidades.stockservice.repository;

import cl.profeperez.entidades.stockservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
