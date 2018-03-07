package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

}
