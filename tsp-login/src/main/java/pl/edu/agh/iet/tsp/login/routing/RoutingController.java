package pl.edu.agh.iet.tsp.login.routing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Bartłomiej Grochal
 */
@RestController
public class RoutingController {

    @RequestMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }

}
