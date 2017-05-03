package pl.edu.agh.iet.tsp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Bartłomiej Grochal
 */
@SpringBootApplication
@EnableOAuth2Sso
public class TrueSocialPlatform extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(TrueSocialPlatform.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/", "/login**")
                .permitAll()
            .anyRequest()
                .authenticated()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}
