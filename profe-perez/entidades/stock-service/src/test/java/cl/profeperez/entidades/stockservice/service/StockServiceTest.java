package cl.profeperez.entidades.stockservice.service;

import cl.profeperez.entidades.stockservice.model.Stock;
import cl.profeperez.entidades.stockservice.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Stock stock1 = new Stock();
        stock1.setId(1L);
        stock1.setLocation("Location 1");

        Stock stock2 = new Stock();
        stock2.setId(2L);
        stock2.setLocation("Location 2");

        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<Stock> stocks = stockService.findAll();

        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setLocation("Location 1");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Optional<Stock> found = stockService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Location 1", found.get().getLocation());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Stock stock = new Stock();
        stock.setLocation("New Location");

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock saved = stockService.save(stock);

        assertEquals("New Location", saved.getLocation());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void testDeleteById() {
        doNothing().when(stockRepository).deleteById(1L);

        stockService.deleteById(1L);

        verify(stockRepository, times(1)).deleteById(1L);
    }
}
