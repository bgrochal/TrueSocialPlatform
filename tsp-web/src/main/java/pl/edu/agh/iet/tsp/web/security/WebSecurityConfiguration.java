package pl.edu.agh.iet.tsp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;
import pl.edu.agh.iet.tsp.database.domain.AuthenticationData;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.login.oauth2.config.ClientConfiguration;
import pl.edu.agh.iet.tsp.login.oauth2.user.AuthenticatedUserDetails;
import pl.edu.agh.iet.tsp.service.UserService;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Bartłomiej Grochal
 */
@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private OAuth2ClientContext clientContext;
    @Autowired private List<ClientConfiguration> clientConfigurations;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login/**", "/**.html", "/templates/**.html")
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
        clientFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler(clientConfiguration));
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


    // FIXME: The code below is not necessary in this class. Try to refactor.
    @Autowired private UserService userService;
    @Autowired private AuthenticatedUserDetails details;

    private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private final ClientConfiguration clientConfiguration;


        AuthenticationSuccessHandler(ClientConfiguration clientConfiguration) {
            this.clientConfiguration = clientConfiguration;
        }


        @Override
        @SuppressWarnings("unchecked")
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            Map<String, Object> authenticatedUserDetails =
                    (Map<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
            AuthenticationData.AuthenticationProvider authenticationProvider =
                    clientConfiguration.getAuthenticationProvider();
            AuthenticationData authenticationData = new AuthenticationData(authenticationProvider,
                    authenticationProvider.getOAuthUniqueIdentifier(authenticatedUserDetails));

            Optional<User> domainUser = userService.getUserByAuthenticationData(authenticationData);
            if (domainUser.isPresent()) {
                setDefaultTargetUrl("/user");
                details.setDomainUser(domainUser.get());
            } else {
                User newUser = new User(null, authenticationData);
                setDefaultTargetUrl("/user/new");
                details.setDomainUser(newUser);
            }
            details.setFullName(authenticationProvider.getOAuthFullName(authenticatedUserDetails));

            super.onAuthenticationSuccess(request, response, authentication);
        }

    }

}
