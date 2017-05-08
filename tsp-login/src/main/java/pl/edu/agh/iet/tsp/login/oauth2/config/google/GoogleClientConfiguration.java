package pl.edu.agh.iet.tsp.login.oauth2.config.google;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import pl.edu.agh.iet.tsp.login.oauth2.config.ClientConfiguration;

/**
 * @author Bartłomiej Grochal
 */
@Configuration
public class GoogleClientConfiguration implements ClientConfiguration {

    @Override
    public AuthorizationCodeResourceDetails getClientProperties() {
        return getGoogleClientProperties();
    }

    @Override
    public ResourceServerProperties getResourceProperties() {
        return getGoogleResourceProperties();
    }

    @Override
    public String getLoginEndpoint() {
        return "/login/google";
    }


    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails getGoogleClientProperties() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties getGoogleResourceProperties() {
        return new ResourceServerProperties();
    }

}
