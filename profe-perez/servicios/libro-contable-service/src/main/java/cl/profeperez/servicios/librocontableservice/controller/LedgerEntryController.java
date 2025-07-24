package cl.profeperez.servicios.librocontableservice.controller;

import cl.profeperez.servicios.librocontableservice.model.LedgerEntry;
import cl.profeperez.servicios.librocontableservice.service.LedgerEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing ledger entries.
 */
@RestController
@RequestMapping("/ledger")
@Slf4j
@Tag(name = "Ledger Entry Controller", description = "CRUD operations for ledger entries")
public class LedgerEntryController {

    private final LedgerEntryService ledgerEntryService;

    public LedgerEntryController(LedgerEntryService ledgerEntryService) {
        this.ledgerEntryService = ledgerEntryService;
    }

    /**
     * Get all ledger entries.
     * @return list of ledger entries
     */
    @Operation(summary = "Get all ledger entries")
    @GetMapping
    public List<LedgerEntry> getAll() {
        log.info("Getting all ledger entries");
        return ledgerEntryService.findAll();
    }

    /**
     * Get ledger entry by ID.
     * @param id ledger entry ID
     * @return ledger entry or 404
     */
    @Operation(summary = "Get ledger entry by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LedgerEntry> getById(@PathVariable Long id) {
        log.info("Getting ledger entry with id {}", id);
        return ledgerEntryService.findById(id)
                .map(entry -> {
                    log.info("Ledger entry found: {}", entry);
                    return ResponseEntity.ok(entry);
                })
                .orElseGet(() -> {
                    log.warn("Ledger entry with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Create a new ledger entry.
     * @param ledgerEntry ledger entry to create
     * @return created ledger entry
     */
    @Operation(summary = "Create a new ledger entry")
    @PostMapping
    public LedgerEntry create(@RequestBody LedgerEntry ledgerEntry) {
        log.info("Creating ledger entry: {}", ledgerEntry);
        return ledgerEntryService.save(ledgerEntry);
    }

    /**
     * Update an existing ledger entry.
     * @param id ledger entry ID
     * @param ledgerEntry ledger entry data to update
     * @return updated ledger entry or 404
     */
    @Operation(summary = "Update an existing ledger entry")
    @PutMapping("/{id}")
    public ResponseEntity<LedgerEntry> update(@PathVariable Long id, @RequestBody LedgerEntry ledgerEntry) {
        log.info("Updating ledger entry with id {}", id);
        return ledgerEntryService.findById(id)
                .map(existing -> {
                    existing.setAccount(ledgerEntry.getAccount());
                    existing.setAmount(ledgerEntry.getAmount());
                    existing.setDescription(ledgerEntry.getDescription());
                    existing.setEntryDate(ledgerEntry.getEntryDate());
                    existing.setType(ledgerEntry.getType());
                    ledgerEntryService.save(existing);
                    log.info("Ledger entry updated: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    log.warn("Ledger entry with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete ledger entry by ID.
     * @param id ledger entry ID
     * @return 204 or 404
     */
    @Operation(summary = "Delete ledger entry by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting ledger entry with id {}", id);
        if (ledgerEntryService.findById(id).isPresent()) {
            ledgerEntryService.deleteById(id);
            log.info("Ledger entry with id {} deleted", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Ledger entry with id {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }
}
