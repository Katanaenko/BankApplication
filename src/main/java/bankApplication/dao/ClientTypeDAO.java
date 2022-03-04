package bankApplication.dao;

import bankApplication.entity.Client;
import bankApplication.entity.ClientType;

/**
 * Interface with methods for DML operations on the client_types table in the database.
 */
public interface ClientTypeDAO {

    /**
     * Returns the clientType from the database with the defined id.
     * @param id identifier of the clientType to get from the database
     * @return the clientType with the id from the parameter
     */
    ClientType getClientTypeById(Integer id);
}
