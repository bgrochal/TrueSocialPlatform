package pl.edu.agh.iet.tsp.core.domain;

import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public class User {

    @Id
    private UUID id;

    private final String username;

    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
