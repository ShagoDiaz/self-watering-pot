package cl.profeperez.servicios.librocontableservice.service;

import cl.profeperez.servicios.librocontableservice.model.LedgerEntry;
import cl.profeperez.servicios.librocontableservice.repository.LedgerEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing ledger entries.
 * Provides CRUD operations and business logic.
 */
@Service
@Slf4j
public class LedgerEntryService {

    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerEntryService(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    /**
     * Retrieve all ledger entries.
     * @return list of ledger entries
     */
    public List<LedgerEntry> findAll() {
        log.info("Fetching all ledger entries");
        return ledgerEntryRepository.findAll();
    }

    /**
     * Find ledger entry by ID.
     * @param id ledger entry ID
     * @return optional ledger entry
     */
    public Optional<LedgerEntry> findById(Long id) {
        log.info("Fetching ledger entry with id {}", id);
        return ledgerEntryRepository.findById(id);
    }

    /**
     * Save or update a ledger entry.
     * @param ledgerEntry ledger entry to save
     * @return saved ledger entry
     */
    public LedgerEntry save(LedgerEntry ledgerEntry) {
        log.info("Saving ledger entry: {}", ledgerEntry);
        return ledgerEntryRepository.save(ledgerEntry);
    }

    /**
     * Delete ledger entry by ID.
     * @param id ledger entry ID
     */
    public void deleteById(Long id) {
        log.info("Deleting ledger entry with id {}", id);
        ledgerEntryRepository.deleteById(id);
    }
}
