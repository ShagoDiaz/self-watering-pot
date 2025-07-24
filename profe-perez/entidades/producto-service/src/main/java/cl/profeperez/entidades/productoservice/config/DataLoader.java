package cl.profeperez.entidades.productoservice.config;

import cl.profeperez.entidades.productoservice.model.Product;
import cl.profeperez.entidades.productoservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * DataLoader to insert sample products on application startup.
 */
@Configuration
@Slf4j
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ProductService productService) {
        return args -> {
            log.info("Loading sample products data...");
            for (int i = 1; i <= 10; i++) {
                Product product = new Product();
                product.setSku("SKU" + i);
                product.setName("Product " + i);
                product.setDescription("Description for product " + i);
                product.setBarcode("BARCODE" + i);
                product.setCategory("Category " + ((i % 3) + 1));
                product.setSupplier("Supplier " + ((i % 2) + 1));
                product.setStockQuantity(100 + i);
                product.setReorderLevel(10);
                product.setCostPrice(10.0 + i);
                product.setSalePrice(15.0 + i);
                product.setTaxRate(0.19);
                product.setUnitOfMeasure("pcs");
                product.setCreatedAt(LocalDateTime.now());
                product.setUpdatedAt(LocalDateTime.now());
                productService.save(product);
            }
            log.info("Sample products data loaded.");
        };
    }
}
