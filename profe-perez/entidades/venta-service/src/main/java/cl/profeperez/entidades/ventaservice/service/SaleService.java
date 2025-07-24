package cl.profeperez.entidades.ventaservice.service;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.ventaservice.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing sales.
 * Provides CRUD operations and business logic.
 */
@Service
@Slf4j
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * Retrieve all sales.
     * @return list of sales
     */
    public List<Sale> findAll() {
        log.info("Fetching all sales");
        return saleRepository.findAll();
    }

    /**
     * Find sale by ID.
     * @param id sale ID
     * @return optional sale
     */
    public Optional<Sale> findById(Long id) {
        log.info("Fetching sale with id {}", id);
        return saleRepository.findById(id);
    }

    /**
     * Save or update a sale.
     * @param sale sale to save
     * @return saved sale
     */
    public Sale save(Sale sale) {
        log.info("Saving sale: {}", sale);
        return saleRepository.save(sale);
    }

    /**
     * Delete sale by ID.
     * @param id sale ID
     */
    public void deleteById(Long id) {
        log.info("Deleting sale with id {}", id);
        saleRepository.deleteById(id);
    }
}
