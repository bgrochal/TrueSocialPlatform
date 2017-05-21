package pl.edu.agh.iet.tsp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Bartłomiej Grochal
 */
@Controller
public class UserViewController {

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public String showNewUser() {
        return "forward:/";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser() {
        return "forward:/";
    }

}
