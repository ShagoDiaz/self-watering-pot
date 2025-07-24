package cl.profeperez.entidades.stockservice.service;

import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.entidades.stockservice.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing stock.
 * Provides CRUD operations and business logic.
 */
@Service
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Retrieve all stock entries.
     * @return list of stock
     */
    public List<Stock> findAll() {
        log.info("Fetching all stock entries");
        return stockRepository.findAll();
    }

    /**
     * Find stock by ID.
     * @param id stock ID
     * @return optional stock
     */
    public Optional<Stock> findById(Long id) {
        log.info("Fetching stock with id {}", id);
        return stockRepository.findById(id);
    }

    /**
     * Save or update a stock entry.
     * @param stock stock to save
     * @return saved stock
     */
    public Stock save(Stock stock) {
        log.info("Saving stock: {}", stock);
        return stockRepository.save(stock);
    }

    /**
     * Delete stock by ID.
     * @param id stock ID
     */
    public void deleteById(Long id) {
        log.info("Deleting stock with id {}", id);
        stockRepository.deleteById(id);
    }
}
