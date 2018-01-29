package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.FloorService;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {

        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "admin/users/list";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addUser(Model model, User user) {
        return "admin/users/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String createUser(Model model, @Valid User user, BindingResult bindingResult) {


        if(bindingResult.hasErrors()){
            return "admin/users/add";
        }

        return "redirect:/users/created";
    }


    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String homePage(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            return "redirect:users";
        }

        model.addAttribute("user", userService.getUserById(Long.valueOf(id)));
        return "admin/users/edit";
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String groupList(Model model) {

        model.addAttribute("groups", userService.findAll());
        return "admin/users/list";
    }

}
