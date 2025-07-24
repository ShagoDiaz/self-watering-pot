package cl.profeperez.entidades.stockservice.controller;

import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.entidades.stockservice.repository.StockRepository;
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
class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        stockRepository.deleteAll();
    }

    @Test
    void testCreateAndGetStock() throws Exception {
        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setQuantity(100);
        stock.setLocation("Test Location");
        stock.setCostPrice(20.0);

        // Create stock
        mockMvc.perform(post("/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is("Test Location")));

        // Get all stocks
        mockMvc.perform(get("/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get stock by ID
        Optional<Stock> savedStock = stockRepository.findAll().stream().findFirst();
        if (savedStock.isPresent()) {
            mockMvc.perform(get("/stock/{id}", savedStock.get().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.quantity", is(100)));
        }
    }

    @Test
    void testUpdateStock() throws Exception {
        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setQuantity(50);
        stock.setLocation("Old Location");
        stock = stockRepository.save(stock);

        stock.setQuantity(75);
        stock.setLocation("New Location");

        mockMvc.perform(put("/stock/{id}", stock.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(75)))
                .andExpect(jsonPath("$.location", is("New Location")));
    }

    @Test
    void testDeleteStock() throws Exception {
        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setQuantity(10);
        stock.setLocation("To Delete");
        stock = stockRepository.save(stock);

        mockMvc.perform(delete("/stock/{id}", stock.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/stock/{id}", stock.getId()))
                .andExpect(status().isNotFound());
    }
}
