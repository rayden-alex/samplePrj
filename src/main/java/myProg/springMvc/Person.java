package myProg.springMvc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "people")
public class Person extends AbstractModel<UUID> implements Model<UUID> {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "username")
    private String username;

    @Override
    public void assignId() {
        id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
