package pl.edu.agh.iet.tsp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.iet.tsp.database.domain.User;
import pl.edu.agh.iet.tsp.service.UserService;
import pl.edu.agh.iet.tsp.service.exception.DuplicateUsernameException;
import pl.edu.agh.iet.tsp.web.controller.json.IdWrapper;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bartłomiej Grochal
 * @author Wojciech Pachuta.
 */
@SuppressWarnings("unchecked")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/me")
    public ResponseEntity user(Principal principal) {
        Map<String, String> userInfo = new HashMap<>();
        Map<String, String> authenticatedUserDetails =
                (Map<String, String>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();

        userInfo.put("name", authenticatedUserDetails.get("name"));
        userInfo.put("email", authenticatedUserDetails.get("email"));

        if (userInfo.get("email") != null) {
            handleAuthenticatedUser(userInfo.get("email"));
            return ResponseEntity.ok(userInfo);
        }

        userInfo.put("errorMessage", "The login request cannot be completed because the email address is unavailable.");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(userInfo);
    }

    private void handleAuthenticatedUser(String userName) {
        // TODO: Check if the user exists in the database. If not, create new entity.
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public IdWrapper addUser(@RequestParam String username) throws DuplicateUsernameException {
        User user = new User(username);
        return new IdWrapper(userService.addUser(user));
    }

}
