package cl.profeperez.entidades.stockservice.config;

import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.entidades.stockservice.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * DataLoader to insert sample stock entries on application startup.
 */
@Configuration
@Slf4j
public class DataLoader {

    @Bean
    CommandLineRunner loadData(StockService stockService) {
        return args -> {
            log.info("Loading sample stock data...");
            for (int i = 1; i <= 10; i++) {
                Stock stock = new Stock();
                stock.setProductId((long) i);
                stock.setQuantity(50 + i);
                stock.setLocation("Location " + i);
                stock.setCostPrice(20.0 + i);
                stock.setLastUpdated(LocalDateTime.now());
                stock.setCreatedAt(LocalDateTime.now());
                stockService.save(stock);
            }
            log.info("Sample stock data loaded.");
        };
    }
}
