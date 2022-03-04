package bankApplication.dao;

import bankApplication.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class with methods for DML operations on the bank_accounts table in the database.
 * Implementation of the AccountDAO interface.
 */
@Repository
public class AccountDAOImpl implements AccountDAO {

    private final SessionFactory sessionFactory;

    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Account getAccountById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Account.class, id);
    }

    @Override
    public List<Account> getAccountsByClientId(Integer clientId) {
        Session session = sessionFactory.getCurrentSession();
        List<Account> accounts= session.createQuery("from Account " +
                "where client = " + clientId).getResultList();
        return accounts;
    }

    @Override
    public Integer saveAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.save(account);
        return account.getId();
    }

    @Override
    public void deleteAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(account);
    }
}
