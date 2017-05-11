package pl.edu.agh.iet.tsp.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wojciech Pachuta.
 */
@Configuration
public class ApplicationConfig {
    public static class AppConfig {
        public final String domain = "http://localhost:8080";
    }

    @Bean
    public AppConfig appConfig() {
        return new AppConfig();
    }
}
