package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ox.kir.seatingplan.entities.BulkUserUpload;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.services.RoleService;
import uk.ac.ox.kir.seatingplan.services.UserService;
import uk.ac.ox.kir.seatingplan.utils.SiteHelper;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "users/list";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String editUser(Model model, @RequestParam(value = "id", required = true) Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        String[] jsFiles = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("metaTitle", "User Details");
        model.addAttribute("groups", userService.findAllGroups());
        model.addAttribute("roles", roleService.findAll());
        return "users/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editAndSaveUser(Model model, @RequestParam(value = "id", required = true) Long id,
                                  @Valid User user, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        String[] jsFiles = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        if(bindingResult.hasErrors()){

            model.addAttribute("groups", userService.findAllGroups());
            model.addAttribute("roles", roleService.findAll());
            return "users/edit";
        }

        userService.update(user);
        redirectAttributes.addFlashAttribute("successMsg", "User details been updated successfully!");
        return "redirect:/user/list";
    }



    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addUser(User user, Model model) {

        String[] jsFiles = {"bootstrap-datepicker.min.js", "select2.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.findAllGroups());
        model.addAttribute("roles", roleService.findAll());
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
            model.addAttribute("roles", roleService.findAll());
            return "users/add";
        }

        userService.createUser(user);
        return "redirect:/user/list";
    }


    //Upload and create bulk users
    @RequestMapping(value = "/user/bulk", method = RequestMethod.GET)
    public String updateUsers() {
        return "users/bulk_create";
    }

    @RequestMapping(value = "/user/confirm/upload", method = RequestMethod.POST)
    public String confirmUpload(
            @RequestParam(name = "file", required = true) MultipartFile file,
            Model model, RedirectAttributes redirectAttributes) {
        //model.addAttribute("userDetails",getUserNewValues(fileData));

        List<User> users = SiteHelper.csvToOLbjects(User.class, file,redirectAttributes);

        Group default_group = groupRepository.findByName("Default Group");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        for (User user : users){
            user.setGroups(Arrays.asList(default_group));
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            userService.create(user);
        }

        if(users.size() > 0){
            redirectAttributes.addFlashAttribute("successMsg", "Users uploaded successfully!");
        }


        return "redirect:/user/list";
    }



    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "user_id") Long id, RedirectAttributes redirectAttributes) {

        userService.delete(id);
        redirectAttributes.addFlashAttribute("successMsg", "User has been deleted successfully!");
        return "redirect:/user/list";

    }




}
