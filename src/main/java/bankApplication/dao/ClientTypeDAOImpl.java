package bankApplication.dao;

import bankApplication.entity.ClientType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Repository class with methods for DML operations on the client_types table in the database.
 * Implementation of the ClientTypeDAO interface.
 */
@Repository
public class ClientTypeDAOImpl implements ClientTypeDAO {

    public ClientTypeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final SessionFactory sessionFactory;

    @Override
    public ClientType getClientTypeById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ClientType.class, id);
    }
}
