package pl.edu.agh.iet.tsp.database.domain;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Map;

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
        FACEBOOK {
            @Override
            public String getOAuthUniqueIdentifier(Map<String, Object> authenticatedUserDetails) {
                // Value of the "id" field is actually a String.
                return (String) authenticatedUserDetails.get("id");
            }
        },
        GITHUB {
            @Override
            public String getOAuthUniqueIdentifier(Map<String, Object> authenticatedUserDetails) {
                // Value of the "id" field is actually an Integer.
                return String.valueOf(authenticatedUserDetails.get("id"));
            }
        },
        GOOGLE {
            @Override
            public String getOAuthUniqueIdentifier(Map<String, Object> authenticatedUserDetails) {
                // Value of the "id" field is actually a String.
                return (String) authenticatedUserDetails.get("sub");
            }
        };


        public abstract String getOAuthUniqueIdentifier(Map<String, Object> authenticatedUserDetails);

        public String getOAuthFullName(Map<String, Object> authenticatedUserDetails) {
            return (String) authenticatedUserDetails.get("name");
        }

    }

}
