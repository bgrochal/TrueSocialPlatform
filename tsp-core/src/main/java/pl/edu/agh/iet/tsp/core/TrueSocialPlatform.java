package pl.edu.agh.iet.tsp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author Bartłomiej Grochal
 */
@SpringBootApplication
@EnableOAuth2Sso
public class TrueSocialPlatform {

    public static void main(String[] args) {
        SpringApplication.run(TrueSocialPlatform.class, args);
    }

}
