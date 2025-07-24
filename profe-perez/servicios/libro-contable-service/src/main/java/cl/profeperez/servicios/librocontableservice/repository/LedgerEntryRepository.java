package cl.profeperez.servicios.librocontableservice.repository;

import cl.profeperez.servicios.librocontableservice.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
}
