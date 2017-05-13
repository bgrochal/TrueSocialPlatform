package pl.edu.agh.iet.tsp.database.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

/**
 * @author Wojciech Pachuta.
 */
@Entity
@Indexes(@Index(value = User.USERNAME, fields = @Field(User.USERNAME), options = @IndexOptions(unique = true)))
public class User {

    public static final String USERNAME = "username";

    @Id
    private ObjectId id;

    private final String username;


    public User() {
        this(null, null);
    }

    public User(ObjectId id, String username) {
        this(username);
        this.id = id;
    }

    public User(String username) {
        this.username = username;
    }


    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

}
