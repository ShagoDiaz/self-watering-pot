package cl.profeperez.entidades.ventaservice.service;

import cl.profeperez.entidades.ventaservice.model.Sale;
import cl.profeperez.entidades.ventaservice.repository.SaleRepository;
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

class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Sale sale1 = new Sale();
        sale1.setId(1L);
        sale1.setCustomer("Customer 1");

        Sale sale2 = new Sale();
        sale2.setId(2L);
        sale2.setCustomer("Customer 2");

        when(saleRepository.findAll()).thenReturn(Arrays.asList(sale1, sale2));

        List<Sale> sales = saleService.findAll();

        assertEquals(2, sales.size());
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setCustomer("Customer 1");

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        Optional<Sale> found = saleService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Customer 1", found.get().getCustomer());
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Sale sale = new Sale();
        sale.setCustomer("New Customer");

        when(saleRepository.save(sale)).thenReturn(sale);

        Sale saved = saleService.save(sale);

        assertEquals("New Customer", saved.getCustomer());
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void testDeleteById() {
        doNothing().when(saleRepository).deleteById(1L);

        saleService.deleteById(1L);

        verify(saleRepository, times(1)).deleteById(1L);
    }
}
