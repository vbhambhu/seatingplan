package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.services.FloorService;
import uk.ac.ox.kir.seatingplan.services.UserService;

import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    FloorService floorService;

    @Autowired
    UserService userService;

    @Autowired
    GroupRepository groupRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            //Todo : add validation here
        }

        model.addAttribute("floors", floorService.findAll());
        return "home";
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setUsername("vkumar");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEmail("test@test.com");
        //user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        user.setCreatedAt(new Date());


        user.setGroup(groupRepository.findOne(Long.valueOf(1)));
        userService.create(user);

        return "done";

    }
}
