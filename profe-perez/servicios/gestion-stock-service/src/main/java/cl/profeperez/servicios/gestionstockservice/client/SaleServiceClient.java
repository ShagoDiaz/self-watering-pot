package cl.profeperez.servicios.gestionstockservice.client;

import cl.profeperez.entidades.ventaservice.model.Sale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "venta-service")
public interface SaleServiceClient {

    @GetMapping("/venta")
    List<Sale> getAllSales();

    @GetMapping("/venta/{id}")
    Sale getSaleById(@PathVariable("id") Long id);

    @PostMapping("/venta")
    Sale addSale(@RequestBody Sale sale);

    @PutMapping("/venta/{id}")
    Sale updateSale(@PathVariable("id") Long id, @RequestBody Sale sale);

    @DeleteMapping("/venta/{id}")
    void deleteSale(@PathVariable("id") Long id);
}
