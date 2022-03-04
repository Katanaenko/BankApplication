package bankApplication.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Account entity DTO class.
 */
public class AccountDTO {

    /**
     * The identifier of the row.
     */
    @Null
    private Integer id;

    /**
     * The identifier of the client linked to the account.
     */
    @NotNull
    private Integer client_id;

    /**
     * The account number.
     */
    @NotNull
    private String number;

    /**
     * The account balance.
     */
    @NotNull
    private Double balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
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
