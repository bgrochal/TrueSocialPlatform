package pl.edu.agh.iet.tsp.core.error;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author Bartłomiej Grochal
 */
@Configuration
public class ErrorHandlersCustomizer {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.html");
            container.addErrorPages(error403Page);
        });
    }

}
