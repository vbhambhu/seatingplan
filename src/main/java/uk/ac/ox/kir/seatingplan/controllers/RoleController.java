package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class RoleController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String groupList(Model model) {

        model.addAttribute("roles", userService.getAllRoles());

        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "roles/list";
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.GET)
    public String addGroup(Role role, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "roles/add";
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public String createGroup(@Valid Role role, BindingResult bindingResult, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        if(bindingResult.hasErrors()){
            return "roles/add";
        }

        userService.saveRole(role);

        return "redirect:/roles";

    }


    @RequestMapping(value = "/role/edit", method = RequestMethod.GET)
    public String editGroup(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            return "redirect:/roles";
        }

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        model.addAttribute("role", userService.getRoleById(Long.valueOf(id)));
        return "roles/edit";
    }


    @RequestMapping(value = "/role/edit", method = RequestMethod.POST)
    public String editGroup(@Valid Role role, BindingResult bindingResult , Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        if(bindingResult.hasErrors()){
            return "roles/edit";
        }


        userService.saveRole(role);
        return "redirect:/roles";

    }
}
