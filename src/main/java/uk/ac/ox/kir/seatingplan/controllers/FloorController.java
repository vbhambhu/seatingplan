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
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class FloorController {

    @Autowired
    FloorService floorService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/floors", method = RequestMethod.GET)
    public String floorlists(Model model) {
        model.addAttribute("floors", floorService.findAll());
        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "floors/list";
    }

    @RequestMapping(value = "/floor/edit/seats", method = RequestMethod.GET)
    public String editSeats(@RequestParam int id, Model model) {
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {"select2.min.js", "seat_editor.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "floors/edit_seats";
    }


    @RequestMapping(value = "/floor/edit/design", method = RequestMethod.GET)
    public String designerPage(@RequestParam int id, Model model) {
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {"designer.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "floors/design";
    }




    @RequestMapping(value = "/floor/add", method = RequestMethod.GET)
    public String addFloor(Floor floor) {
        return "floors/add";
    }


    @RequestMapping(value = "/floor/add", method = RequestMethod.POST)
    public String createFloor(@Valid Floor floor, BindingResult bindingResult) {



        if (bindingResult.hasErrors()) {
            return "admin/floors/add";
        }

        floorService.create(floor);

        return "redirect:/floors";
    }



    @RequestMapping(value = "/floor/edit/name", method = RequestMethod.GET)
    public String editName(@RequestParam int id, Model model) {

        model.addAttribute("floor", floorService.getFloorById(Long.valueOf(id)) );
        return "floors/edit_name";
    }


    @RequestMapping(value = "/floor/edit/name", method = RequestMethod.POST)
    public String saveeditName(@Valid Floor floor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "floors/edit_name";
        }


        Floor floor1 =  floorService.getFloorById(floor.getId());
        floor1.setName(floor.getName());


        floorService.update(floor1);

        return "redirect:/floors";
    }




}
