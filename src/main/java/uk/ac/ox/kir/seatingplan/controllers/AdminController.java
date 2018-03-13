package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ox.kir.seatingplan.services.UserService;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String usersList(Model model) {
        return "admin";
    }

}
