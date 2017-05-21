package pl.edu.agh.iet.tsp.login.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;

/**
 * @author Bartłomiej Grochal
 */
public interface ClientConfiguration {

    AuthenticationData.AuthenticationProvider getAuthenticationProvider();

    AuthorizationCodeResourceDetails getClientProperties();

    ResourceServerProperties getResourceProperties();

    String getLoginEndpoint();

}
