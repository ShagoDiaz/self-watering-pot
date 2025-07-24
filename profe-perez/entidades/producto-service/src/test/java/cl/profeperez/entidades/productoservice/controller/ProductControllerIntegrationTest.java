package cl.profeperez.entidades.productoservice.controller;

import cl.profeperez.entidades.productoservice.model.Product;
import cl.profeperez.entidades.productoservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;

@SpringBootTest(exclude = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class
})
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testCreateAndGetProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setSku("SKU123");
        product.setDescription("Test Description");
        product.setBarcode("BAR123");
        product.setCategory("Category1");
        product.setSupplier("Supplier1");
        product.setStockQuantity(100);
        product.setReorderLevel(10);
        product.setCostPrice(10.0);
        product.setSalePrice(15.0);
        product.setTaxRate(0.19);
        product.setUnitOfMeasure("pcs");

        // Create product
        mockMvc.perform(post("/producto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Product")));

        // Get all products
        mockMvc.perform(get("/producto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get product by ID
        Optional<Product> savedProduct = productRepository.findAll().stream().findFirst();
        if (savedProduct.isPresent()) {
            mockMvc.perform(get("/producto/{id}", savedProduct.get().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sku", is("SKU123")));
        }
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("Old Name");
        product.setSku("OLD123");
        product = productRepository.save(product);

        product.setName("New Name");
        product.setSku("NEW123");

        mockMvc.perform(put("/producto/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Name")))
                .andExpect(jsonPath("$.sku", is("NEW123")));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product product = new Product();
        product.setName("To Delete");
        product = productRepository.save(product);

        mockMvc.perform(delete("/producto/{id}", product.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/producto/{id}", product.getId()))
                .andExpect(status().isNotFound());
    }
}
