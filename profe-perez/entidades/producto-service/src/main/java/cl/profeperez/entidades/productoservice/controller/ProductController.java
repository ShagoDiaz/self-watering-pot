package cl.profeperez.entidades.productoservice.controller;

import cl.profeperez.entidades.productoservice.model.Product;
import cl.profeperez.entidades.productoservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/producto")
@Slf4j
@Tag(name = "Producto Controller", description = "CRUD operations for products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products.
     * @return list of products
     */
    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> getAll() {
        log.info("Getting all products");
        return productService.findAll();
    }

    /**
     * Get product by ID.
     * @param id product ID
     * @return product or 404
     */
    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        log.info("Getting product with id {}", id);
        return productService.findById(id)
                .map(product -> {
                    log.info("Product found: {}", product);
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    log.warn("Product with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Create a new product.
     * @param product product to create
     * @return created product
     */
    @Operation(summary = "Create a new product")
    @PostMapping
    public Product create(@RequestBody Product product) {
        log.info("Creating product: {}", product);
        return productService.save(product);
    }

    /**
     * Update an existing product.
     * @param id product ID
     * @param product product data to update
     * @return updated product or 404
     */
    @Operation(summary = "Update an existing product")
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        log.info("Updating product with id {}", id);
        return productService.findById(id)
                .map(existing -> {
                    existing.setSku(product.getSku());
                    existing.setName(product.getName());
                    existing.setDescription(product.getDescription());
                    existing.setBarcode(product.getBarcode());
                    existing.setCategory(product.getCategory());
                    existing.setSupplier(product.getSupplier());
                    existing.setStockQuantity(product.getStockQuantity());
                    existing.setReorderLevel(product.getReorderLevel());
                    existing.setCostPrice(product.getCostPrice());
                    existing.setSalePrice(product.getSalePrice());
                    existing.setTaxRate(product.getTaxRate());
                    existing.setUnitOfMeasure(product.getUnitOfMeasure());
                    productService.save(existing);
                    log.info("Product updated: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    log.warn("Product with id {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete product by ID.
     * @param id product ID
     * @return 204 or 404
     */
    @Operation(summary = "Delete product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting product with id {}", id);
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            log.info("Product with id {} deleted", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Product with id {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }
}
