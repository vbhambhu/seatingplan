package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class GroupController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String groupList(Model model) {

        model.addAttribute("groups", userService.getAllGroups());

        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "groups/list";
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String addGroup(Group group, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "groups/add";
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    public String createGroup(@Valid Group group, BindingResult bindingResult, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        if(bindingResult.hasErrors()){
            return "groups/add";
        }

        userService.saveGroup(group);

        return "redirect:/groups";

    }


    @RequestMapping(value = "/group/edit", method = RequestMethod.GET)
    public String editGroup(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            return "redirect:/groups";
        }

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        model.addAttribute("group", userService.getGroupById(Long.valueOf(id)));
        return "groups/edit";
    }


    @RequestMapping(value = "/group/edit", method = RequestMethod.POST)
    public String editGroup(@Valid Group group, BindingResult bindingResult , Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        if(bindingResult.hasErrors()){
            return "groups/edit";
        }


        userService.saveGroup(group);
        return "redirect:/groups";

    }


}
