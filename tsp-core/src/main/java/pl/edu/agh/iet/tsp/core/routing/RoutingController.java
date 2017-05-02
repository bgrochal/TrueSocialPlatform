package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bartłomiej Grochal
 */
@RestController
public class RoutingController {

    @RequestMapping("/")
    public String index() {
        return "True Social Platform";
    }

}
