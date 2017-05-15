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
    public static final String AUTHENTICATION_DATA = "authenticationData";

    @Id private ObjectId id;

    @Embedded private final AuthenticationData authenticationData;
    private final String username;


    public User() {
        this(null, null, null);
    }

    public User(ObjectId id, String username, AuthenticationData authenticationData) {
        this(username, authenticationData);
        this.id = id;
    }

    public User(String username, AuthenticationData authenticationData) {
        this.username = username;
        this.authenticationData = authenticationData;
    }


    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

}
