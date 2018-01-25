package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.services.FloorService;

import javax.validation.Valid;

@Controller
public class DesignController {

    @Autowired
    FloorService floorService;


    @RequestMapping(value = "/design", method = RequestMethod.GET)
    public String designerPage(@RequestParam int id, Model model) {


        model.addAttribute("floors", floorService.findAll());
        return "design";
    }


    @RequestMapping(value = "/designer", method = RequestMethod.GET)
    public String designerPage(Floor floor) {
        return "designer";
    }


    @RequestMapping(value = "/designer", method = RequestMethod.POST)
    public String createFloor(@Valid Floor floor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "designer";
        }

        floorService.save(floor);

        return "redirect:/design?id="+floor.getId();
    }


    }
