package bankApplication.dao;

import bankApplication.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Repository class with methods for DML operations on the clients table in the database.
 * Implementation of the ClientDAO interface.
 */
@Repository
public class ClientDAOImpl implements ClientDAO {

    private final SessionFactory sessionFactory;

    public ClientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Client getClientById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }

    @Override
    public Integer saveClient(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.save(client);
        return client.getId();
    }

    @Override
    public void deleteClient(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(client);
    }

}
