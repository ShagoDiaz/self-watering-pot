package cl.profeperez.entidades.stockservice.controller;

import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.entidades.stockservice.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing stock.
 */
@RestController
@RequestMapping("/stock")
@Slf4j
@Tag(name = "Stock Controller", description = "CRUD operations for stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Get all stock entries.
     * @return list of stock
     */
    @Operation(summary = "Get all stock entries")
    @GetMapping
    public List<Stock> getAll() {
        log.info("Getting all stock entries");
        return stockService.findAll();
    }

    /**
     * Get stock by ID.
     * @param id stock ID
     * @return stock or 404
     */
    @Operation(summary = "Get stock by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable Long id) {
        log.info("Getting stock with id {}", id);
        return stockService.findById(id)
                .map(stock -> {
                    log.info("Stock found: {}", stock);
                    return ResponseEntity.ok(stock);
                })
                .orElseGet(() -> {
                    log.warn("Stock with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Create a new stock entry.
     * @param stock stock to create
     * @return created stock
     */
    @Operation(summary = "Create a new stock entry")
    @PostMapping
    public Stock create(@RequestBody Stock stock) {
        log.info("Creating stock: {}", stock);
        return stockService.save(stock);
    }

    /**
     * Update an existing stock entry.
     * @param id stock ID
     * @param stock stock data to update
     * @return updated stock or 404
     */
    @Operation(summary = "Update an existing stock entry")
    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock stock) {
        log.info("Updating stock with id {}", id);
        return stockService.findById(id)
                .map(existing -> {
                    existing.setProductId(stock.getProductId());
                    existing.setQuantity(stock.getQuantity());
                    existing.setLocation(stock.getLocation());
                    existing.setCostPrice(stock.getCostPrice());
                    existing.setLastUpdated(stock.getLastUpdated());
                    existing.setCreatedAt(stock.getCreatedAt());
                    stockService.save(existing);
                    log.info("Stock updated: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    log.warn("Stock with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete stock by ID.
     * @param id stock ID
     * @return 204 or 404
     */
    @Operation(summary = "Delete stock by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting stock with id {}", id);
        if (stockService.findById(id).isPresent()) {
            stockService.deleteById(id);
            log.info("Stock with id {} deleted", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Stock with id {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }
}
