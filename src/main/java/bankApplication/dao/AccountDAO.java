package bankApplication.dao;

import bankApplication.entity.Account;

import java.util.List;

/**
 * Interface with methods for DML operations on the bank_accounts table in the database.
 */
public interface AccountDAO {

    /**
     * Returns the account from the database with the defined id.
     * @param id identifier of the account to get from the database
     * @return the account with the id from the parameter
     */
    Account getAccountById(Integer id);

    /**
     * Returns the account list from the database with the defined clientId.
     * @param clientId identifier of the client whose accounts to get from the database
     * @return the account list with the clientId from the parameter
     */
    List<Account> getAccountsByClientId(Integer clientId);

    /**
     * Save a new account to the database or update the existing one.
     * @param account the account to save or update
     * @return identifier of the saved or updated account
     */
    Integer saveAccount(Account account);

    /**
     * Delete the account.
     * @param account the account to delete
     */
    void deleteAccount(Account account);
}
