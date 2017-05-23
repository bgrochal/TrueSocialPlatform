package pl.edu.agh.iet.tsp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.login.oauth2.user.AuthenticatedUserDetails;
import pl.edu.agh.iet.tsp.service.UserService;
import pl.edu.agh.iet.tsp.service.exception.DuplicateUsernameException;
import pl.edu.agh.iet.tsp.web.controller.json.IdWrapper;

import java.util.HashMap;


/**
 * @author Bartłomiej Grochal
 * @author Wojciech Pachuta.
 */
@SuppressWarnings("unchecked")
@RestController
public class UserController {

    @Autowired private UserService userService;
    @Autowired private AuthenticatedUserDetails authenticatedUserDetails;


    @RequestMapping("/me")
    public HashMap<String, String> currentUser() {
        HashMap<String, String> currentUserDetails = new HashMap<>();
        currentUserDetails.put("name", authenticatedUserDetails.getFullName());
        currentUserDetails.put("username", authenticatedUserDetails.getDomainUser().getUsername());
        currentUserDetails.put("id", authenticatedUserDetails.getDomainUser().getId() != null ?
                authenticatedUserDetails.getDomainUser().getId().toHexString() : null);

        return currentUserDetails;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public IdWrapper addUser(@RequestParam String username) throws DuplicateUsernameException {
        User user = new User(username, authenticatedUserDetails.getDomainUser().getAuthenticationData());
        userService.addUser(user);

        authenticatedUserDetails.setDomainUser(user);
        return new IdWrapper(userService.addUser(user));
    }

}
