package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.FloorService;
import uk.ac.ox.kir.seatingplan.services.UserService;

@RestController
public class APIController {

    @Autowired
    FloorService floorService;

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "api/design/save", method = RequestMethod.POST)
    public String saveFloor(@RequestParam("floorid") String floorid,
                            @RequestParam("svg_content") String svg_content){

        Floor floor = floorService.getFloorById(Long.valueOf(floorid));
        floor.setSvgContent(svg_content);
        floorService.save(floor);
        return "Saved successfully!";

    }


    @ResponseBody
    @RequestMapping(value = "api/design/get", method = RequestMethod.GET)
    public Floor getSeatingPlan(@RequestParam("floorid") String floorid){
        Floor floor = floorService.getFloorById(Long.valueOf(floorid));
        return floor;

    }


    @ResponseBody
    @RequestMapping(value = "api/user/get", method = RequestMethod.GET)
    public User getUserDetail(@RequestParam("userid") String userid){

        User user = userService.getUserById(Long.valueOf(userid));
        return user;

    }




}
