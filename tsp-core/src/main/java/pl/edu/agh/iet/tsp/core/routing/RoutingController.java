package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Bartłomiej Grochal
 */
@RestController
public class RoutingController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
