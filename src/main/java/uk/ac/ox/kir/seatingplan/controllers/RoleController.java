package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.services.RoleService;

import javax.validation.Valid;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public String rolesList(Model model) {

        String[] jsFiles = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("roles", roleService.findAll());
        return "roles/list";
    }


    @RequestMapping(value = "/role/add", method = RequestMethod.GET)
    public String addRole(Model model, Role role) {

        String[] jsFiles = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "roles/add";
    }


    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public String addRole(Model model, @Valid Role role, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        if(roleService.findByName(role.getName()) != null){
            bindingResult.rejectValue("name", "name","Role already exists.");
        }

        if(bindingResult.hasErrors()){
            String[] jsFiles = {"jscolor.min.js"};
            model.addAttribute("jsFiles", jsFiles);
            return "roles/add";
        }

        roleService.create(role);
        redirectAttributes.addFlashAttribute("successMsg", "New role had been created successfully!");
        return "redirect:/role/list";
    }


    @RequestMapping(value = "/role/edit", method = RequestMethod.GET)
    public String editRole(Model model, Role role,
                            @RequestParam(value = "id", required = true) Long id) {

        model.addAttribute("role",roleService.getById(id) );
        String[] jsFiles = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "roles/edit";
    }

    @RequestMapping(value = "/role/edit", method = RequestMethod.POST)
    public String editRole(Model model,
                           @Valid Role role,
                           BindingResult bindingResult,
                           @RequestParam(value = "id", required = true) Long id,
                           RedirectAttributes redirectAttributes) {

        if(roleService.findRoleByNameExceptId(role.getName(), role.getId()) != null){
            bindingResult.rejectValue("name", "name", "This Role is already exists.");
        }


        if(bindingResult.hasErrors()){
            String[] jsFiles = {"jscolor.min.js"};
            model.addAttribute("jsFiles", jsFiles);
            return "roles/edit";
        }

        roleService.update(role);
        redirectAttributes.addFlashAttribute("successMsg", "Role has been edit successfully!");
        return "redirect:/role/list";
    }

    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "role_id") Long id, RedirectAttributes redirectAttributes) {

        Role role = roleService.getById(id);

        if(role.getUsers().size() > 0){
            redirectAttributes.addFlashAttribute("errorMsg", "Cant' delete role. Role is assigned to existing users.");
        } else{
            roleService.delete(id);
            redirectAttributes.addFlashAttribute("successMsg", "Role has been deleted successfully!");
        }

        return "redirect:/role/list";

    }

}
