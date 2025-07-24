package cl.profeperez.entidades.productoservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Producto Service API", version = "1.0", description = "API for Producto Service"))
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("Starting Producto Service Application...");
        SpringApplication.run(Application.class, args);
        log.info("Producto Service Application started.");
    }
}
