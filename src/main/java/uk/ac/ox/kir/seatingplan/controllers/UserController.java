package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.Group;
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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        System.out.println(userDetails.getAuthorities());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());

        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "admin/users/list";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String addUser(Model model, User user) {


        String jsFiles[] = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.getAllGroups());


        return "admin/users/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String createUser(Model model, @Valid User user, BindingResult bindingResult) {

        String jsFiles[] = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.getAllGroups());

        if(!userService.usernameExists(user.getUsername())){
            bindingResult.rejectValue("username", "username", "Username already exists.");
        } else if(!userService.emailExists(user.getEmail())){
            bindingResult.rejectValue("email", "email", "Email address already exists");
        }

        if(bindingResult.hasErrors()){
            return "admin/users/add";
        }

        userService.create(user);

        return "redirect:/users";
    }


    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            return "redirect:users";
        }

        String jsFiles[] = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.getAllGroups());

        model.addAttribute("user", userService.getUserById(Long.valueOf(id)));
        return "admin/users/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String saveEditUser(Model model,
                               @Valid User user,
                               BindingResult bindingResult) {


        String jsFiles[] = {"bootstrap-datepicker.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        model.addAttribute("groups", userService.getAllGroups());

        if(!userService.duplicateUsername(user.getId(), user.getUsername())){
            bindingResult.rejectValue("username", "username", "This username is used for another user.");
        } else if(!userService.duplicateEmail(user.getId(), user.getEmail())){
            bindingResult.rejectValue("email", "email", "This email is used for another user.");
        }

        if(bindingResult.hasErrors()){
            return "admin/users/edit";
        }

        User dbuser = userService.getUserById(user.getId());

        //set new values
        dbuser.setFirstName(user.getFirstName());
        dbuser.setLastName(user.getLastName());
        dbuser.setUsername(user.getUsername());
        dbuser.setEmail(user.getEmail());
        dbuser.setStartDate(user.getStartDate());
        dbuser.setEndDate(user.getEndDate());
        dbuser.setComputerAddress(user.getComputerAddress());
       // dbuser.setGroup(user.getGroup());
        dbuser.setPI(user.isPI());
        dbuser.setComment(user.getComment());

        userService.update(dbuser);

        return "redirect:/users";

    }



    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String groupList(Model model) {

        model.addAttribute("groups", userService.getAllGroups());

        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "admin/groups/list";
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String addGroup(Group group, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);

        return "admin/groups/add";
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    public String createGroup(@Valid Group group, BindingResult bindingResult, Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        if(bindingResult.hasErrors()){
            return "admin/groups/add";
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
        return "admin/groups/edit";
    }


    @RequestMapping(value = "/group/edit", method = RequestMethod.POST)
    public String editGroup(@Valid Group group, BindingResult bindingResult , Model model) {

        String jsFiles[] = {"jscolor.min.js"};
        model.addAttribute("jsFiles", jsFiles);


        if(bindingResult.hasErrors()){
            return "admin/groups/edit";
        }



        userService.saveGroup(group);

        return "redirect:/groups";

    }


}
