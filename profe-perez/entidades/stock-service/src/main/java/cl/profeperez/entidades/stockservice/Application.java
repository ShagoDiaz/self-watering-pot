package cl.profeperez.entidades.stockservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Stock Service API", version = "1.0", description = "API for Stock Service"))
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("Starting Stock Service Application...");
        SpringApplication.run(Application.class, args);
        log.info("Stock Service Application started.");
    }
}
