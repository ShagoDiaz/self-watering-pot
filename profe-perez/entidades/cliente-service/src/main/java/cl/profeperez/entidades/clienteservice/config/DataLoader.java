package cl.profeperez.entidades.clienteservice.config;

import cl.profeperez.entidades.clienteservice.model.Client;
import cl.profeperez.entidades.clienteservice.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * DataLoader to insert sample clients on application startup.
 */
@Configuration
@Slf4j
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ClientService clientService) {
        return args -> {
            log.info("Loading sample clients data...");
            for (int i = 1; i <= 10; i++) {
                Client client = new Client();
                client.setName("Client " + i);
                client.setEmail("client" + i + "@example.com");
                client.setPhone("555-000" + i);
                client.setAddress("Address " + i);
                client.setCreatedAt(LocalDateTime.now());
                client.setUpdatedAt(LocalDateTime.now());
                clientService.save(client);
            }
            log.info("Sample clients data loaded.");
        };
    }
}
