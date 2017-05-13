package pl.edu.agh.iet.tsp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;
import pl.edu.agh.iet.tsp.login.oauth2.config.ClientConfiguration;

import javax.servlet.Filter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bartłomiej Grochal
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired OAuth2ClientContext clientContext;
    @Autowired List<ClientConfiguration> clientConfigurations;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login/**", "/**.html")
                .permitAll()
            .anyRequest()
                .authenticated()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }


    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(clientConfigurations.stream()
                .map(this::ssoFilter)
                .collect(Collectors.toList()));
        return filter;
    }

    private Filter ssoFilter(ClientConfiguration clientConfiguration) {
        OAuth2ClientAuthenticationProcessingFilter clientFilter
                = new OAuth2ClientAuthenticationProcessingFilter(clientConfiguration.getLoginEndpoint());
        OAuth2RestTemplate clientTemplate =
                new OAuth2RestTemplate(clientConfiguration.getClientProperties(), clientContext);
        clientFilter.setRestTemplate(clientTemplate);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                clientConfiguration.getResourceProperties().getUserInfoUri(),
                clientConfiguration.getClientProperties().getClientId());
        tokenServices.setRestTemplate(clientTemplate);
        clientFilter.setTokenServices(tokenServices);

        return clientFilter;
    }

}
