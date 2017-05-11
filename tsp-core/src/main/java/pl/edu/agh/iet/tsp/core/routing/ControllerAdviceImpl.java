package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.iet.tsp.core.service.exception.DuplicateUsernameException;
import pl.edu.agh.iet.tsp.core.service.exception.NoSuchCommentException;
import pl.edu.agh.iet.tsp.core.service.exception.NoSuchPostException;

/**
 * @author Wojciech Pachuta.
 */
@ControllerAdvice
public class ControllerAdviceImpl {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists")
    @ExceptionHandler(DuplicateUsernameException.class)
    public void handleDuplicateUsername() {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment with this id does not exist")
    @ExceptionHandler(NoSuchCommentException.class)
    public void handleNoSuchComment() {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post with this id does not exist")
    @ExceptionHandler(NoSuchPostException.class)
    public void handleNoSuchPost() {
    }

}
