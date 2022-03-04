package bankApplication.controller;

import bankApplication.configuration.ApplicationConfiguration;
import bankApplication.dto.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@Transactional
class AccountControllerTest {

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
    public void getAccountByAccountIdRightId() throws Exception {
        int accountId = 1;
        mockMvc.perform(get("/account?accountId=" + accountId)
                        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAccountByAccountIdWrongId() throws Exception {
        int accountId = 99;
        mockMvc.perform(get("/account?accountId=" + accountId)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAccountsByClientIdRightId() throws Exception {
        int clientId = 1;
        mockMvc.perform(get("/accounts?clientId=" + clientId)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAccountsByClientIdWrongId() throws Exception {
        int clientId = 99;
        mockMvc.perform(get("/accounts?clientId=" + clientId)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    public void addAccountNoId() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setClient_id(1);
        accountDTO.setNumber(RandomStringUtils.randomAlphabetic(10));
        accountDTO.setBalance(13.2);
        String requestBody = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addAccountId() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setClient_id(1);
        accountDTO.setNumber(RandomStringUtils.randomAlphabetic(10));
        accountDTO.setBalance(13.2);
        String requestBody = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Rollback
    public void deleteAccountRightId() throws Exception {
        int id = 1;
        mockMvc.perform(delete("/account?id=" + id)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAccountWrongId() throws Exception {
        int id = 99;
        mockMvc.perform(delete("/account?id=" + id)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
}