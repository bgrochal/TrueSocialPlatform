package pl.edu.agh.iet.tsp.login.oauth2.user;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.edu.agh.iet.tsp.database.domain.User;

/**
 * @author Bartłomiej Grochal
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticatedUserDetails {

    private User domainUser;
    private String fullName;


    public User getDomainUser() {
        return domainUser;
    }

    public void setDomainUser(User domainUser) {
        this.domainUser = domainUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
