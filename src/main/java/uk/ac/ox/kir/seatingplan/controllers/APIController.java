package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.services.FloorService;

@RestController
public class APIController {

    @Autowired
    FloorService floorService;


    @ResponseBody
    @RequestMapping(value = "api/design/save", method = RequestMethod.POST)
    public String saveFloor(@RequestParam("floorid") String floorid,
                            @RequestParam("svg_content") String svg_content){

        Floor floor = floorService.getFloorById(Long.valueOf(floorid));
        floor.setSvgContent(svg_content);
        floorService.save(floor);
        return "saved";

    }


    @ResponseBody
    @RequestMapping(value = "api/design/get", method = RequestMethod.GET)
    public Floor getSeatingPlan(@RequestParam("floorid") String floorid){
        Floor floor = floorService.getFloorById(Long.valueOf(floorid));
        return floor;

    }




}
