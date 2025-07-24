package cl.profeperez.servicios.gestionstockservice.client;

import cl.profeperez.servicios.librocontableservice.model.LedgerEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "libro-contable-service")
public interface LedgerServiceClient {

    @GetMapping("/ledger")
    List<LedgerEntry> getAllLedgerEntries();

    @GetMapping("/ledger/{id}")
    LedgerEntry getLedgerEntryById(@PathVariable("id") Long id);

    @PostMapping("/ledger")
    LedgerEntry addLedgerEntry(@RequestBody LedgerEntry ledgerEntry);

    @PutMapping("/ledger/{id}")
    LedgerEntry updateLedgerEntry(@PathVariable("id") Long id, @RequestBody LedgerEntry ledgerEntry);

    @DeleteMapping("/ledger/{id}")
    void deleteLedgerEntry(@PathVariable("id") Long id);
}
