package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.services.FloorService;

@Controller
public class HomeController {

    @Autowired
    FloorService floorService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(@RequestParam(required = false) String id, Model model) {

        if(id == null){
            //Todo : add validation here
        }

        model.addAttribute("floors", floorService.findAll());
        return "home";
    }

}
