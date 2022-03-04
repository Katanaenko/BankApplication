package bankApplication.service;


import bankApplication.dto.AccountDTO;
import bankApplication.dto.ClientDTO;

import java.util.List;

/**
 * Interface with the logic to connect the data access layer and the controller layer
 * of the application.
 */
public interface BankApplicationService {

    /**
     * Add an account(without id).
     * @param accountDTO the account we want to add
     * @return the identifier of the saved account
     */
    Integer addAccount(AccountDTO accountDTO);

    /**
     * Get account by accountId.
     * @param accountId the identifier of the account we want to get
     * @return the account with the identifier from the parameter
     */
    AccountDTO getAccountByAccountId(Integer accountId);

    /**
     * Delete the account with the id from the parameter.
     * @param id the identifier of the account to delete
     */
    void deleteAccount(Integer id);

    /**
     * Get account list by clientId.
     * @param clientId the identifier of the client whose accounts to get
     * @return the list of accounts connected to the client with the identifier from the parameter
     */
    List<AccountDTO> getAccountsByClientId(Integer clientId);

    /**
     * Add a client(without id).
     * @param clientDTO the client to add
     * @return the identifier of the saved client
     */
    Integer addClient(ClientDTO clientDTO);

    /**
     * Delete the client with the id from the parameter.
     * @param id the identifier of the client to delete
     */
    void deleteClient(Integer id);

}
