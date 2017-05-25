package pl.edu.agh.iet.tsp.web.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

        return currentUserDetails;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public IdWrapper addUser(@RequestParam String username) throws DuplicateUsernameException {
        User user = new User(username, authenticatedUserDetails.getDomainUser().getAuthenticationData());
        userService.addUser(user);

        authenticatedUserDetails.setDomainUser(user);
        return new IdWrapper(userService.addUser(user));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void removeUserAndAllHisContent(@PathVariable("userId") String userId) {
        userService.removeUserAndHisContent(new ObjectId(userId));
    }

}
