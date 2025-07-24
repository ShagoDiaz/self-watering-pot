package cl.profeperez.servicios.librocontableservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Libro Contable Service API", version = "1.0", description = "API for Libro Contable Service"))
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("Starting Libro Contable Service Application...");
        SpringApplication.run(Application.class, args);
        log.info("Libro Contable Service Application started.");
    }
}
