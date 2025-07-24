package cl.profeperez.servicios.gestionstockservice.controller;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.servicios.gestionstockservice.service.IntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to expose integration endpoints for stock and sales management.
 */
@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Integration Controller", description = "Endpoints for integrated stock and sales operations")
public class IntegrationController {

    private final IntegrationService integrationService;

    /**
     * Register a sale, update stock, and create ledger entry.
     * @param sale the sale to register
     * @return HTTP 200 OK
     */
    @Operation(summary = "Register a sale and update stock and ledger")
    @PostMapping("/register-sale")
    public ResponseEntity<Void> registerSale(@RequestBody Sale sale) {
        log.info("Received request to register sale: {}", sale);
        integrationService.registerSale(sale);
        return ResponseEntity.ok().build();
    }

    /**
     * Add stock and create ledger entry.
     * @param stock the stock to add
     * @return HTTP 200 OK
     */
    @Operation(summary = "Add stock and create ledger entry")
    @PostMapping("/add-stock")
    public ResponseEntity<Void> addStock(@RequestBody Stock stock) {
        log.info("Received request to add stock: {}", stock);
        integrationService.addStock(stock);
        return ResponseEntity.ok().build();
    }
}
