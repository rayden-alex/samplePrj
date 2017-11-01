package myProg.springMvc;

import java.util.UUID;

public class PersonDTO {
    public final UUID id;
    public final String username;

    public PersonDTO(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}