package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.UserService;

@Controller
public class GroupContorller {

//    @Autowired
//    UserService userService;
//
//    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
//    public String usersList(Model model) {
//
//        String[] jsFiles = {"datatables.min.js"};
//        model.addAttribute("jsFiles", jsFiles);
//
//        model.addAttribute("users", userService.findAll());
//        return "users/list";
//    }
//
//
//    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
//    public String addUser(User user) {
//
//       // model.addAttribute("users", userService.findAll());
//        return "users/add";
//    }
}
