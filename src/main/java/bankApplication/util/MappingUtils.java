package bankApplication.util;

import bankApplication.controllerExceptionHandling.NoSuchIdException;
import bankApplication.dao.AccountDAO;
import bankApplication.dao.ClientDAO;
import bankApplication.dao.ClientTypeDAO;
import bankApplication.dto.AccountDTO;
import bankApplication.dto.ClientDTO;
import bankApplication.entity.Account;
import bankApplication.entity.Client;
import bankApplication.entity.ClientType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Util Class with methods to transform Entity to DTO and DTO to Entity.
 */
@Service
@Transactional
public class MappingUtils {

    private final ClientDAO clientDAO;
    private final ClientTypeDAO clientTypeDAO;
    private final AccountDAO accountDAO;

    public MappingUtils(ClientDAO clientDAO, ClientTypeDAO clientTypeDAO, AccountDAO accountDAO) {
        this.clientDAO = clientDAO;
        this.clientTypeDAO = clientTypeDAO;
        this.accountDAO = accountDAO;
    }

    /**
     * Transform Account to AccountDTO.
     * @param account the object of the Account class
     * @return object of the AccountDTO class
     */
    public AccountDTO accountToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setClient_id(account.getClient().getId());
        accountDTO.setNumber(account.getNumber());
        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }

    /**
     * Transform AccountDTO to Account.
     * @param accountDTO the object of the AccountDTO class
     * @return object of the Account class
     */
    public Account accountDTOToAccount(AccountDTO accountDTO) {
        Account account = null;
        if (accountDTO.getId() != null) {
            account = accountDAO.getAccountById(accountDTO.getId());
        }
        if (account == null) {
            account = new Account();
        }
        account.setId(accountDTO.getId());
        Client client = clientDAO.getClientById(accountDTO.getClient_id());
        if (client == null) {
            throw new NoSuchIdException("There is no client with the id = " + accountDTO.getClient_id() + " in the database");
        }
        client.addAccountToAccounts(account);
        account.setNumber(accountDTO.getNumber());
        account.setBalance(accountDTO.getBalance());
        return account;
    }

    /**
     * Method to transform Client to ClientDTO.
     * @param client the object of the Client class
     * @return object of the ClientDTO class
     */
    public ClientDTO clientToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setType_id(client.getType().getId());
        return clientDTO;
    }

    /**
     * Method to transform ClientDTO to Client.
     * @param clientDTO the object of the ClientDTO class
     * @return object of the Client class
     */
    public Client clientDTOToClient(ClientDTO clientDTO) {
        Client client = null;
        if (clientDTO.getId() != null) {
            client = clientDAO.getClientById(clientDTO.getId());
        }
        if(client == null){
            client = new Client();
        }
        client.setId(clientDTO.getId());
        ClientType clientType = clientTypeDAO.getClientTypeById(clientDTO.getType_id());
        if(clientType == null){
            throw new NoSuchIdException("There is no clientType with the id = " + clientDTO.getType_id() + " in the database");
        }
        clientType.addClientToClients(client);
        return client;
    }
}
