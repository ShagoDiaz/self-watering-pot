package cl.profeperez.entidades.clienteservice.service;

import cl.profeperez.entidades.clienteservice.model.Client;
import cl.profeperez.entidades.clienteservice.repository.ClientRepository;
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

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Client 1");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Client 2");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientService.findAll();

        assertEquals(2, clients.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Client 1");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> found = clientService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals("Client 1", found.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Client client = new Client();
        client.setName("New Client");

        when(clientRepository.save(client)).thenReturn(client);

        Client saved = clientService.save(client);

        assertEquals("New Client", saved.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDeleteById() {
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteById(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }
}
