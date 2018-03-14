package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {

        String[] jsFiles = {"svglib/svg.min.js", "viewer.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "home";
    }

}
