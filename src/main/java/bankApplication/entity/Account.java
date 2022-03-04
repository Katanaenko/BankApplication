package bankApplication.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity class. The bank_accounts database table.
 */
@Entity
@Table(name = "accounts")
public class Account {

    /**
     * The identifier of the row.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The client linked to the account.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,  CascadeType.REFRESH, CascadeType.MERGE })
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * The account number.
     */
    @Column(name = "number")
    private String number;

    /**
     * The account balance.
     */
    @Column(name = "balance")
    private Double balance;

    /**
     * Hibernate system field. Is used for optimistic locks.
     */
    @Version
    @Column(name = "version")
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
