package cl.profeperez.entidades.ventaservice.controller;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.ventaservice.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing sales.
 */
@RestController
@RequestMapping("/venta")
@Slf4j
@Tag(name = "Sale Controller", description = "CRUD operations for sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    /**
     * Get all sales.
     * @return list of sales
     */
    @Operation(summary = "Get all sales")
    @GetMapping
    public List<Sale> getAll() {
        log.info("Getting all sales");
        return saleService.findAll();
    }

    /**
     * Get sale by ID.
     * @param id sale ID
     * @return sale or 404
     */
    @Operation(summary = "Get sale by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Sale> getById(@PathVariable Long id) {
        log.info("Getting sale with id {}", id);
        return saleService.findById(id)
                .map(sale -> {
                    log.info("Sale found: {}", sale);
                    return ResponseEntity.ok(sale);
                })
                .orElseGet(() -> {
                    log.warn("Sale with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Create a new sale.
     * @param sale sale to create
     * @return created sale
     */
    @Operation(summary = "Create a new sale")
    @PostMapping
    public Sale create(@RequestBody Sale sale) {
        log.info("Creating sale: {}", sale);
        return saleService.save(sale);
    }

    /**
     * Update an existing sale.
     * @param id sale ID
     * @param sale sale data to update
     * @return updated sale or 404
     */
    @Operation(summary = "Update an existing sale")
    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(@PathVariable Long id, @RequestBody Sale sale) {
        log.info("Updating sale with id {}", id);
        return saleService.findById(id)
                .map(existing -> {
                    existing.setProductId(sale.getProductId());
                    existing.setQuantity(sale.getQuantity());
                    existing.setPrice(sale.getPrice());
                    existing.setCustomer(sale.getCustomer());
                    existing.setSaleDate(sale.getSaleDate());
                    existing.setStatus(sale.getStatus());
                    saleService.save(existing);
                    log.info("Sale updated: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    log.warn("Sale with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete sale by ID.
     * @param id sale ID
     * @return 204 or 404
     */
    @Operation(summary = "Delete sale by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting sale with id {}", id);
        if (saleService.findById(id).isPresent()) {
            saleService.deleteById(id);
            log.info("Sale with id {} deleted", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Sale with id {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }
}
