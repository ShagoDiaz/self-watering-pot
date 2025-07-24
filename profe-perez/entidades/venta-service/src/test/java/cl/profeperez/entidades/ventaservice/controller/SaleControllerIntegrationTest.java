package cl.profeperez.entidades.ventaservice.controller;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.ventaservice.repository.SaleRepository;
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
class SaleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
    }

    @Test
    void testCreateAndGetSale() throws Exception {
        Sale sale = new Sale();
        sale.setProductId(1L);
        sale.setQuantity(10);
        sale.setPrice(100.0);
        sale.setCustomer("Test Customer");
        sale.setStatus("COMPLETED");

        // Create sale
        mockMvc.perform(post("/venta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer", is("Test Customer")));

        // Get all sales
        mockMvc.perform(get("/venta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get sale by ID
        Optional<Sale> savedSale = saleRepository.findAll().stream().findFirst();
        if (savedSale.isPresent()) {
            mockMvc.perform(get("/venta/{id}", savedSale.get().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.price", is(100.0)));
        }
    }

    @Test
    void testUpdateSale() throws Exception {
        Sale sale = new Sale();
        sale.setProductId(1L);
        sale.setQuantity(5);
        sale.setPrice(50.0);
        sale.setCustomer("Old Customer");
        sale = saleRepository.save(sale);

        sale.setQuantity(15);
        sale.setPrice(150.0);
        sale.setCustomer("New Customer");

        mockMvc.perform(put("/venta/{id}", sale.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(15)))
                .andExpect(jsonPath("$.price", is(150.0)))
                .andExpect(jsonPath("$.customer", is("New Customer")));
    }

    @Test
    void testDeleteSale() throws Exception {
        Sale sale = new Sale();
        sale.setProductId(1L);
        sale.setQuantity(10);
        sale.setCustomer("To Delete");
        sale = saleRepository.save(sale);

        mockMvc.perform(delete("/venta/{id}", sale.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/venta/{id}", sale.getId()))
                .andExpect(status().isNotFound());
    }
}
