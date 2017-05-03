package pl.edu.agh.iet.tsp.login.oauth2.configuration.facebook;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import pl.edu.agh.iet.tsp.login.oauth2.configuration.ClientConfiguration;

/**
 * @author Bartłomiej Grochal
 */
@Configuration
public class FacebookClientConfiguration implements ClientConfiguration {

    @Override
    public AuthorizationCodeResourceDetails getClientProperties() {
        return getFacebookClientProperties();
    }

    @Override
    public ResourceServerProperties getResourceProperties() {
        return getFacebookResourceProperties();
    }

    @Override
    public String getLoginEndpoint() {
        return "/login/facebook";
    }


    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails getFacebookClientProperties() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties getFacebookResourceProperties() {
        return new ResourceServerProperties();
    }

}
