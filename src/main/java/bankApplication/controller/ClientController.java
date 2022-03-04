package bankApplication.controller;

import bankApplication.dto.ClientDTO;
import bankApplication.service.BankApplicationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Controller class with the deprecated endpoints for the Client API.
 */
@RestController
@Deprecated
public class ClientController {

    private final BankApplicationService bankApplicationService;

    public ClientController(BankApplicationService bankApplicationService) {
        this.bankApplicationService = bankApplicationService;
    }

    /**
     * Add a client.
     * @param clientDTO the client to add
     * @return identifier of the added client
     */
    @PostMapping("/client")
    public Integer addClient(@RequestBody @Valid ClientDTO clientDTO) {
        return bankApplicationService.addClient(clientDTO);
    }

    /**
     * Delete the client with the id from the request parameters.
     * @return message with the info about successful/unsuccessful deletion
     */
    @DeleteMapping("/client")
    public String deleteClient(@RequestParam Integer id) {
        bankApplicationService.deleteClient(id);
        return "Client with the id = " + id + " was deleted";
    }
}
