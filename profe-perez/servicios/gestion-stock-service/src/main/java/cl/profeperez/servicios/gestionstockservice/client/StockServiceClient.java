package cl.profeperez.servicios.gestionstockservice.client;

import cl.profeperez.entidades.stockservice.model.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "stock-service")
public interface StockServiceClient {

    @GetMapping("/stock")
    List<Stock> getAllStock();

    @GetMapping("/stock/{id}")
    Stock getStockById(@PathVariable("id") Long id);

    @PostMapping("/stock")
    Stock addStock(@RequestBody Stock stock);

    @PutMapping("/stock/{id}")
    Stock updateStock(@PathVariable("id") Long id, @RequestBody Stock stock);

    @DeleteMapping("/stock/{id}")
    void deleteStock(@PathVariable("id") Long id);
}
