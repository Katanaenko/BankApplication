package bankApplication.dao;

import bankApplication.entity.Client;

/**
 * Interface with methods for DML operations on the clients table in the database.
 */
public interface ClientDAO {

    /**
     * Returns the client from the database with the defined id.
     * @param id identifier of the client to get from the database
     * @return the client with the id from the parameter
     */
    Client getClientById(Integer id);

    /**
     * Save a new client to the database.
     * @param client the client to save
     * @return identifier of the saved client
     */
    Integer saveClient(Client client);

    /**
     * Delete the client.
     * @param client the client to delete
     */
    void deleteClient(Client client);
}
