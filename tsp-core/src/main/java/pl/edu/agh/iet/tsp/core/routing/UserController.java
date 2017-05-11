package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.iet.tsp.core.domain.User;
import pl.edu.agh.iet.tsp.core.routing.json.IdWrapper;
import pl.edu.agh.iet.tsp.core.service.UserService;
import pl.edu.agh.iet.tsp.core.service.exception.DuplicateUsernameException;

/**
 * @author Wojciech Pachuta.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public IdWrapper addUser(@RequestParam String username) throws DuplicateUsernameException {
        User user = new User(username);
        return new IdWrapper(userService.addUser(user));
    }

}
