package bankApplication.kafka.consumer;

import bankApplication.dto.ClientDTO;
import bankApplication.service.BankApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Class to handle messages from kafka.
 */
@Service
@PropertySource("classpath:application.properties")
public class KafkaMessageConsumer {

    private final ObjectMapper objectMapper;

    private final BankApplicationService bankApplicationService;

    public KafkaMessageConsumer(BankApplicationService bankApplicationService) {
        this.objectMapper = new ObjectMapper();
        this.bankApplicationService = bankApplicationService;
    }

    /**
     * Consumes the messages from the topics related to client.
     * @param message the message with the data about a client
     */
    @KafkaListener(topics = "#{'${kafka.consumertopics}'.split(',')}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        handleKafkaMessage(message, topic);
    }

    /**
     * Handel a message from of kafka. Way of handeling depends on a topic.
     * @param message the message from the topic
     * @param topic the topic
     */
    private void handleKafkaMessage(String message, String topic) throws JsonProcessingException {
        ClientDTO clientDTO;
        switch (topic) {
            case ("addClient") :
                clientDTO = objectMapper.readValue(message, ClientDTO.class);
                bankApplicationService.addClient(clientDTO);
                break;
            case ("deleteClient") :
                Integer id = Integer.parseInt(message);
                bankApplicationService.deleteClient(id);
                break;
            default :
                throw new KafkaException("Wrong topic");
        }
    }
}
