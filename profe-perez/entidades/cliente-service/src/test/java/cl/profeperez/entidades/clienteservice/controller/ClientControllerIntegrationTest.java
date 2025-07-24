package cl.profeperez.entidades.clienteservice.controller;

import cl.profeperez.entidades.clienteservice.model.Client;
import cl.profeperez.entidades.clienteservice.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    void testCreateAndGetClient() throws Exception {
        Client client = new Client();
        client.setName("Test Client");
        client.setEmail("test@example.com");
        client.setPhone("1234567890");
        client.setAddress("Test Address");

        // Create client
        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Client")));

        // Get all clients
        mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get client by ID
        Optional<Client> savedClient = clientRepository.findAll().stream().findFirst();
        if (savedClient.isPresent()) {
            mockMvc.perform(get("/cliente/{id}", savedClient.get().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", is("test@example.com")));
        }
    }

    @Test
    void testUpdateClient() throws Exception {
        Client client = new Client();
        client.setName("Old Name");
        client.setEmail("old@example.com");
        client.setPhone("1111111111");
        client.setAddress("Old Address");
        client = clientRepository.save(client);

        client.setName("New Name");
        client.setEmail("new@example.com");

        mockMvc.perform(put("/cliente/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Name")))
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    void testDeleteClient() throws Exception {
        Client client = new Client();
        client.setName("To Delete");
        client = clientRepository.save(client);

        mockMvc.perform(delete("/cliente/{id}", client.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cliente/{id}", client.getId()))
                .andExpect(status().isNotFound());
    }
}
