package bankApplication.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class. The clients database table.
 */
@Entity
@Table(name = "clients")
public class Client {

    /**
     * The identifier of the row.
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * The clientType linked to the client.
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ClientType type;

    /**
     * The list of accounts linked to the client.
     */
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "client")
    private Set<Account> accounts;

    /**
     * Hibernate system field. Is used for optimistic locks.
     */
    @Version
    @Column(name = "version")
    private Integer version;

    public void setType(ClientType type) {
        this.type = type;
    }

    /**
     * Add an account to the list of accounts.
     * @param account the account to add
     */
    public void addAccountToAccounts(Account account) {
        if(accounts == null) {
            accounts = new HashSet<>();
        }
        accounts.add(account);
        account.setClient(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientType getType() {
        return type;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
