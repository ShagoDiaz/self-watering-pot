package cl.profeperez.entidades.ventaservice.config;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.ventaservice.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * DataLoader to insert sample sales on application startup.
 */
@Configuration
@Slf4j
public class DataLoader {

    @Bean
    CommandLineRunner loadData(SaleService saleService) {
        return args -> {
            log.info("Loading sample sales data...");
            for (int i = 1; i <= 10; i++) {
                Sale sale = new Sale();
                sale.setProductId((long) i);
                sale.setQuantity(5 + i);
                sale.setPrice(100.0 + i);
                sale.setCustomer("Customer " + i);
                sale.setSaleDate(LocalDateTime.now());
                sale.setStatus("COMPLETED");
                saleService.save(sale);
            }
            log.info("Sample sales data loaded.");
        };
    }
}
