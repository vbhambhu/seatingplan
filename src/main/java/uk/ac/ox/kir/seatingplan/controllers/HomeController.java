package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.services.FloorService;

@Controller
public class HomeController {

    @Autowired
    FloorService floorService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model, @RequestParam(required = false) Long floorid) {

        if(floorid == null){
            //floorService  created_at
           Floor floor = floorService.findFirstCreateFloor();

           floorid = floor.getId();
         }

         model.addAttribute("floorid", floorid);

        String[] jsFiles = {"svglib/svg.min.js", "viewer.js"};
        model.addAttribute("jsFiles", jsFiles);
        return "home";
    }

}
