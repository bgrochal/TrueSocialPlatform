package pl.edu.agh.iet.tsp.database.domain;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author Bartłomiej Grochal
 */
@Embedded
public class AuthenticationData {

    public static final String AUTHENTICATION_PROVIDER = "authenticationProvider";
    public static final String UNIQUE_ID = "uniqueID";

    private final AuthenticationProvider authenticationProvider;
    private final String uniqueID;


    public AuthenticationData() {
        this(null, null);
    }

    public AuthenticationData(AuthenticationProvider authenticationProvider, String uniqueID) {
        this.authenticationProvider = authenticationProvider;
        this.uniqueID = uniqueID;
    }


    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public String getUniqueID() {
        return uniqueID;
    }


    public enum AuthenticationProvider {
        FACEBOOK, GITHUB, GOOGLE
    }

}
