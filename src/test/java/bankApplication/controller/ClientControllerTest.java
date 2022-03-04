package bankApplication.controller;

import bankApplication.configuration.ApplicationConfiguration;
import bankApplication.dto.ClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@Transactional
class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addClientNoId() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setType_id(1);
        String requestBody = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addClientExistingId() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1);
        clientDTO.setType_id(1);
        String requestBody = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addClientWrongClientTypeId() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(99);
        clientDTO.setType_id(99);
        String requestBody = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addClientNoClientTypeId() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(99);
        String requestBody = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Rollback
    public void addClientRightId() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(99);
        clientDTO.setType_id(1);
        String requestBody = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClientWrongId() throws Exception {
        mockMvc.perform(delete("/client")
                        .param("id", "99")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteClientNoId() throws Exception {
        mockMvc.perform(delete("/client")).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Rollback
    public void deleteClientRightId() throws Exception {
        mockMvc.perform(delete("/client")
                        .param("id", "1")).andDo(print())
                .andExpect(status().isOk());
    }
}