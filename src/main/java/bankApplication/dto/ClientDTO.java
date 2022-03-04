package bankApplication.dto;


import javax.validation.constraints.NotNull;

/**
 * Client entity DTO class.
 */
public class ClientDTO {

    /**
     * The identifier of the row.
     */
    @NotNull
    private Integer id;

    /**
     * The identifier of the clientType linked to the client.
     */
    @NotNull
    private Integer type_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }
}
