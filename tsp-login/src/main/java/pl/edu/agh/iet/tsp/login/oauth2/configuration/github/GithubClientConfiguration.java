package pl.edu.agh.iet.tsp.login.oauth2.configuration.github;

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
public class GithubClientConfiguration implements ClientConfiguration {

    @Override
    public AuthorizationCodeResourceDetails getClientProperties() {
        return getGithubClientProperties();
    }

    @Override
    public ResourceServerProperties getResourceProperties() {
        return getGithubResourceProperties();
    }

    @Override
    public String getLoginEndpoint() {
        return "/login/github";
    }


    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails getGithubClientProperties() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties getGithubResourceProperties() {
        return new ResourceServerProperties();
    }

}
