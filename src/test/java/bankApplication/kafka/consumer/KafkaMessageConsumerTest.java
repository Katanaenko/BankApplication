package bankApplication.kafka.consumer;

import bankApplication.configuration.ApplicationConfiguration;
import bankApplication.dao.ClientDAO;
import bankApplication.dto.ClientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@Transactional
class KafkaMessageConsumerTest {

    @Autowired
    private KafkaMessageConsumer kafkaMessageConsumer;

    @Autowired
    private ClientDAO clientDAO;

    private ObjectMapper objectMapper;

    private ClientDTO clientDTO;

    @BeforeEach
    public void setUpBeforeClass() {
        objectMapper = new ObjectMapper();

        //Configure the client to be added through kafka topic
        clientDTO = new ClientDTO();
        clientDTO.setId(99);
        clientDTO.setType_id(1);
    }

    @Test
    @Rollback
    public void testAddClient() throws JsonProcessingException {
        String kafkaMessagePayload = objectMapper.writeValueAsString(clientDTO);
        String topic = "addClient";
        kafkaMessageConsumer.consume(kafkaMessagePayload, topic);
        Assertions.assertNotNull(clientDAO.getClientById(clientDTO.getId()));
    }

    @Test
    @Rollback
    public void testADeleteClient() throws JsonProcessingException {
        //Add a client
        String kafkaMessagePayload = objectMapper.writeValueAsString(clientDTO);
        String topic = "addClient";
        kafkaMessageConsumer.consume(kafkaMessagePayload, topic);
        //Delete the client
        topic = "deleteClient";
        kafkaMessageConsumer.consume(clientDTO.getId().toString(), topic);
        Assertions.assertNull(clientDAO.getClientById(99));
    }
}