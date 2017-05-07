package pl.edu.agh.iet.tsp.login.oauth2.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * @author Bartłomiej Grochal
 */
public interface ClientConfiguration {

    AuthorizationCodeResourceDetails getClientProperties();

    ResourceServerProperties getResourceProperties();

    String getLoginEndpoint();

}
