package bankApplication.controller;

import bankApplication.dto.AccountDTO;
import bankApplication.service.BankApplicationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class with the endpoints for the Account API.
 */
@RestController
public class AccountController {

    private final BankApplicationService bankApplicationService;

    public AccountController(BankApplicationService bankApplicationService) {
        this.bankApplicationService = bankApplicationService;
    }

    /**
     * Return the account with the defined id.
     * @param accountId identifier of the account to get
     * @return the account with the id from the parameter
     */
    @GetMapping("/account")
    public AccountDTO getAccountByAccountId(@RequestParam Integer accountId) {
        return bankApplicationService.getAccountByAccountId(accountId);
    }

    /**
     * Return the account list with the defined clientId.
     * @param clientId identifier of the client whose accounts to get
     * @return the account list with the clientId from the parameter
     */
    @GetMapping("/accounts")
    public List<AccountDTO> getAccountsByClientId(@RequestParam Integer clientId) {
        return bankApplicationService.getAccountsByClientId(clientId);
    }

    /**
     * Add an account.
     * @param accountDTO the account to add
     * @return identifier of the added account
     */
    @PostMapping("/account")
    public Integer addAccount(@RequestBody @Valid AccountDTO accountDTO) {
        return bankApplicationService.addAccount(accountDTO);
    }

    /**
     * Delete the account with the id from the request parameters.
     * @return message with the info about successful/unsuccessful deletion
     */
    @DeleteMapping("/account")
    public String deleteAccount(@RequestParam Integer id) {
        bankApplicationService.deleteAccount(id);
        return "Account with the id = " + id + "was deleted";
    }
}
