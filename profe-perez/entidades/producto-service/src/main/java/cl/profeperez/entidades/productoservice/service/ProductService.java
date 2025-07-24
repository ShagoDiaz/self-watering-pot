package cl.profeperez.entidades.productoservice.service;

import cl.profeperez.entidades.productoservice.model.Product;
import cl.profeperez.entidades.productoservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing products.
 * Provides CRUD operations and business logic.
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieve all products.
     * @return list of products
     */
    public List<Product> findAll() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    /**
     * Find product by ID.
     * @param id product ID
     * @return optional product
     */
    public Optional<Product> findById(Long id) {
        log.info("Fetching product with id {}", id);
        return productRepository.findById(id);
    }

    /**
     * Save or update a product.
     * @param product product to save
     * @return saved product
     */
    public Product save(Product product) {
        log.info("Saving product: {}", product);
        return productRepository.save(product);
    }

    /**
     * Delete product by ID.
     * @param id product ID
     */
    public void deleteById(Long id) {
        log.info("Deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}
