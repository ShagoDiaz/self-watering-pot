package cl.profeperez.servicios.gestionstockservice.service;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.servicios.librocontableservice.model.LedgerEntry;
import cl.profeperez.servicios.gestionstockservice.client.LedgerServiceClient;
import cl.profeperez.servicios.gestionstockservice.client.SaleServiceClient;
import cl.profeperez.servicios.gestionstockservice.client.StockServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service to integrate stock, sales, and ledger services for full flow coverage.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntegrationService {

    private final StockServiceClient stockServiceClient;
    private final SaleServiceClient saleServiceClient;
    private final LedgerServiceClient ledgerServiceClient;

    /**
     * Register a sale, update stock, and create ledger entry.
     * @param sale the sale to register
     */
    public void registerSale(Sale sale) {
        log.info("Registering sale: {}", sale);
        // Save sale
        Sale savedSale = saleServiceClient.addSale(sale);

        // Decrease stock quantity
        Stock stock = stockServiceClient.getStockById(sale.getProductId());
        if (stock != null) {
            int newQuantity = stock.getQuantity() - sale.getQuantity();
            stock.setQuantity(newQuantity);
            stockServiceClient.updateStock(stock.getId(), stock);
            log.info("Updated stock quantity for product {}: {}", stock.getId(), newQuantity);
        } else {
            log.warn("Stock not found for product id {}", sale.getProductId());
        }

        // Create ledger entry for sale
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setAccount("Sales");
        ledgerEntry.setAmount(sale.getPrice() * sale.getQuantity());
        ledgerEntry.setDescription("Sale registered for product " + sale.getProductId());
        ledgerEntry.setEntryDate(LocalDateTime.now());
        ledgerEntry.setType("CREDIT");
        ledgerServiceClient.addLedgerEntry(ledgerEntry);
        log.info("Created ledger entry for sale: {}", ledgerEntry);
    }

    /**
     * Add stock and create ledger entry for stock addition.
     * @param stock the stock to add
     */
    public void addStock(Stock stock) {
        log.info("Adding stock: {}", stock);
        // Save stock
        Stock savedStock = stockServiceClient.addStock(stock);

        // Create ledger entry for stock addition
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setAccount("Inventory");
        ledgerEntry.setAmount(stock.getCostPrice() * stock.getQuantity());
        ledgerEntry.setDescription("Stock added for product " + stock.getId());
        ledgerEntry.setEntryDate(LocalDateTime.now());
        ledgerEntry.setType("DEBIT");
        ledgerServiceClient.addLedgerEntry(ledgerEntry);
        log.info("Created ledger entry for stock addition: {}", ledgerEntry);
    }
}
