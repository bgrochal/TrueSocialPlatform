package pl.edu.agh.iet.tsp.login.routing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bartłomiej Grochal
 */
@SuppressWarnings("unchecked")
@RestController
public class RoutingController {

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

}
