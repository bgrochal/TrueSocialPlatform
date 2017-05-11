package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.iet.tsp.core.service.exception.DuplicateUsernameException;

/**
 * @author Wojciech Pachuta.
 */
@ControllerAdvice
public class ControllerAdviceImpl {
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists")
    @ExceptionHandler(DuplicateUsernameException.class)
    public void handleDuplicateUsername() {
    }

}
