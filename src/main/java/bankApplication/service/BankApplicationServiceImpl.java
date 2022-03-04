package bankApplication.service;

import bankApplication.controllerExceptionHandling.NoSuchIdException;
import bankApplication.dao.AccountDAO;
import bankApplication.dao.ClientDAO;
import bankApplication.dto.AccountDTO;
import bankApplication.dto.ClientDTO;
import bankApplication.entity.Account;
import bankApplication.entity.Client;
import bankApplication.util.MappingUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class with the logic to connect the data access layer and the controller layer of the application.
 * Implementation of the BankApplicationService interface.
 */
@Service
@Transactional
public class BankApplicationServiceImpl implements BankApplicationService {

    private final MappingUtils mappingUtils;
    private final AccountDAO accountDAO;
    private final ClientDAO clientDAO;

    public BankApplicationServiceImpl(MappingUtils mappingUtils, AccountDAO accountDAO, ClientDAO clientDAO) {
        this.mappingUtils = mappingUtils;
        this.accountDAO = accountDAO;
        this.clientDAO = clientDAO;
    }

    @Override
    public Integer addAccount(AccountDTO accountDTO) {
        Account account = mappingUtils.accountDTOToAccount(accountDTO);
        return accountDAO.saveAccount(account);
    }

    @Override
    public AccountDTO getAccountByAccountId(Integer accountId) {
        Account account = accountDAO.getAccountById(accountId);
        if(account == null){
            throw new NoSuchIdException("There is no account with the id = " + accountId + " in the database");
        }
        return mappingUtils.accountToAccountDTO(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        Account account = accountDAO.getAccountById(id);
        if(account == null){
            throw new NoSuchIdException("There is no account with the id = " + id + " in the database");
        };
        accountDAO.deleteAccount(account);
    }

    @Override
    public List<AccountDTO> getAccountsByClientId(Integer clientId) {
        List<Account> accounts = accountDAO.getAccountsByClientId(clientId);
        if(accounts.size() == 0){
            throw new NoSuchIdException("There is no accounts with the client_id = " + clientId + " in the database");
        }
        return accounts.stream().map(mappingUtils::accountToAccountDTO).collect(Collectors.toList());
    }

    @Override
    public Integer addClient(ClientDTO clientDTO) {
        if(clientDAO.getClientById(clientDTO.getId()) != null){
            throw new RuntimeException("There is already a client with the id = " + clientDTO.getId() + " in the database");
        }
        Client client = mappingUtils.clientDTOToClient(clientDTO);
        return clientDAO.saveClient(client);
    }

    @Override
    public void deleteClient(Integer id) {
        Client client = clientDAO.getClientById(id);
        if(client == null){
            throw new NoSuchIdException("There is no client with the id = " + id + " in the database");
        }
        clientDAO.deleteClient(client);
    }
}
