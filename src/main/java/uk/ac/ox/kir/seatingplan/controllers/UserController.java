package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String usersList(Model model) {

        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @RequestMapping(value = "/user/details", method = RequestMethod.GET)
    public String editUser(Model model, @RequestParam(value = "id", required = true) Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        String[] jsFiles = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("metaTitle", "User Details");

        return "users/details";
    }



    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addUser(User user, Model model) {

        String[] jsFiles = {"bootstrap-datepicker.min.js", "select2.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.findAllGroups());
        return "users/add";

    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String validateAndSaveUser(Model model, @Valid User user, BindingResult bindingResult) {

        if(userService.getUserByUsername(user.getUsername()) != null){
            bindingResult.rejectValue("username", "username", "Users with this username is already exists.");
        }

        if(userService.getUserByEmail(user.getEmail()) != null){
            bindingResult.rejectValue("email", "email", "Users with this email is already exists.");
        }

        if(bindingResult.hasErrors()){
            String[] jsFiles = {"bootstrap-datepicker.min.js", "select2.min.js"};
            model.addAttribute("jsFiles", jsFiles);
            model.addAttribute("groups", userService.findAllGroups());
            return "users/add";
        }

        userService.createUser(user);
        return "redirect:/user/list";
    }
}
