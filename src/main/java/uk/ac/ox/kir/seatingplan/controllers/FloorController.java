package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @RequestMapping(value = "/floor/list", method = RequestMethod.GET)
    public String floorlists(Model model) {
        model.addAttribute("floors", floorService.findAll());
        String jsFiles[] = {"datatables.min.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "floors/list";
    }




    @RequestMapping(value = "/floor/edit/design", method = RequestMethod.GET)
    public String designerPage(@RequestParam int id, Model model) {
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("users", userService.findAll());

        String jsFiles[] = {"jscolor.min.js", "notify.min.js", "designer.js",
                "svglib/svg.min.js","svglib/svg.draggable.js",
                "svglib/svg.resize.js","svglib/svg.select.js",};
        model.addAttribute("jsFiles", jsFiles);

        model.addAttribute("designSidebar", true);

        return "floors/design";
    }


    @RequestMapping(value = "/floor/edit/seats", method = RequestMethod.GET)
    public String editSeats(@RequestParam int id, Model model) {

        model.addAttribute("users", userService.findAll());
        String jsFiles[] = {  "notify.min.js",
                "svglib/svg.min.js","svglib/svg.resize.js",
                "svglib/svg.select.js", "seat_editor.js"};
        model.addAttribute("jsFiles", jsFiles);

        model.addAttribute("editorSidebar", true);
        return "floors/edit_seats";
    }


    @RequestMapping(value = "/floor/add", method = RequestMethod.GET)
    public String addFloor(Floor floor) {
        return "floors/add";
    }


    @RequestMapping(value = "/floor/add", method = RequestMethod.POST)
    public String createFloor(@Valid Floor floor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "floors/add";
        }

        floorService.create(floor);
        return "redirect:/floor/list";
    }



    @RequestMapping(value = "/floor/edit/name", method = RequestMethod.GET)
    public String editName(@RequestParam int id, Model model) {
        model.addAttribute("floor", floorService.getFloorById(Long.valueOf(id)) );
        return "floors/edit_name";
    }


    @RequestMapping(value = "/floor/edit/name", method = RequestMethod.POST)
    public String saveEditName(@Valid Floor floor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "floors/edit_name";
        }

        Floor floor1 =  floorService.getFloorById(floor.getId());
        floor1.setName(floor.getName());
        floorService.update(floor1);
        redirectAttributes.addFlashAttribute("successMsg", "Floor name has been updated successfully!");
        return "redirect:/floor/list";
    }


    @RequestMapping(value = "/floor/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "floorId") Long id, RedirectAttributes redirectAttributes) {

        floorService.delete(id);
        redirectAttributes.addFlashAttribute("successMsg", "Floor has been deleted successfully!");
        return "redirect:/floor/list";

    }
}
