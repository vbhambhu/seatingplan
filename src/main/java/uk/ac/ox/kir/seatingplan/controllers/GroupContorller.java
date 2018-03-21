package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.services.GroupService;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class GroupContorller {

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public String groupList(Model model) {
        String[] jsFiles = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", groupService.findAll());
        return "groups/list";
    }


    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String addGroup(Group group, Model model) {
        String[] jsFiles = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "groups/add";
    }


    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    public String validateAndCreateGroup(Model model, @Valid Group group, BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {


        if(groupService.findByName(group.getName()) != null){
            bindingResult.rejectValue("name", "name", "This group is already exists.");
        }

        if(bindingResult.hasErrors()){
            String[] jsFiles = {"jscolor.min.js"};
            model.addAttribute("jsFiles", jsFiles);
            return "groups/add";
        }

        groupService.create(group);
        redirectAttributes.addFlashAttribute("successMsg", "New group been added successfully!");
        return "redirect:/group/list";
    }



    @RequestMapping(value = "/group/edit", method = RequestMethod.GET)
    public String editGroup(Group group, Model model,
                            @RequestParam(value = "id", required = true) Long id) {

        model.addAttribute("group",groupService.getById(id) );
        String[] jsFiles = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "groups/edit";
    }


    @RequestMapping(value = "/group/edit", method = RequestMethod.POST)
    public String validateAndSaveEditGroup(Model model,
                                           @Valid Group group,
                                           BindingResult bindingResult,
                                           @RequestParam(value = "id", required = true) Long id) {

        if(groupService.findByNameExceptId(group.getName(), group.getId()) != null){
            bindingResult.rejectValue("name", "name", "This group is already exists.");
        }


        if(bindingResult.hasErrors()){
            String[] jsFiles = {"jscolor.min.js"};
            model.addAttribute("jsFiles", jsFiles);
            return "groups/edit";
        }

        groupService.update(group);
        return "redirect:/group/list";
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "group_id") Long id, RedirectAttributes redirectAttributes) {

        Group group = groupService.getById(id);

        if(group.getUsers().size() > 0){
            redirectAttributes.addFlashAttribute("errorMsg", "Cant' delete group. Group is assigned to existing users.");
        } else{
            groupService.delete(id);
            redirectAttributes.addFlashAttribute("successMsg", "Group has been deleted successfully!");
        }

        return "redirect:/group/list";

    }




}
