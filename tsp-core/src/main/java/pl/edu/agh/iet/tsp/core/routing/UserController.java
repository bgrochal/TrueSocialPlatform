package pl.edu.agh.iet.tsp.core.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.iet.tsp.core.db.UserRepository;
import pl.edu.agh.iet.tsp.core.domain.User;

/**
 * @author Wojciech Pachuta.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam String username) {
        return userRepository.insert(new User(username)).getId().toString();
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@PathVariable String username) {
        return userRepository.findByUsername(username).getId().toString();
    }

}
