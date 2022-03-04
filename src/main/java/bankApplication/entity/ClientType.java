package bankApplication.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class. The client_type database table.
 */
@Entity
@Table(name = "client_types")
public class ClientType {

    /**
     * The identifier of the row.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The client type name.
     */
    @Column(name = "type")
    private String type;

    /**
     * he list of clients with the type.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,  CascadeType.REFRESH, CascadeType.MERGE },
            mappedBy = "type")
    private Set<Client> clients;

    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * Add a client to the list of clients.
     * @param client the client to add
     */
    public void addClientToClients(Client client) {
        if(clients == null){
            clients = new HashSet<>();
        }
        clients.add(client);
        client.setType(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
