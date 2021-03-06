package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.FloorService;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class APIController {

    @Autowired
    FloorService floorService;

    @Autowired
    UserService userService;



    @ResponseBody
    @RequestMapping(value = "/api/user/search", method = RequestMethod.GET)
    public List<User> searchUser(@RequestParam("q") String q){
        return userService.search(q);
    }


    @ResponseBody
    @RequestMapping(value = "/api/user/get", method = RequestMethod.GET)
    public User searchUser(@RequestParam("id") Long id){
        return userService.getUserById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/api/design/get", method = RequestMethod.GET)
    public Floor getSeatingPlan(@RequestParam("floorid") String floorId){
        return floorService.getFloorById(Long.valueOf(floorId));
    }

    @ResponseBody
    @RequestMapping(value = "api/seat/save", method = RequestMethod.POST)
    public String saveFloor(@RequestParam("floorid") String floorid, @RequestParam("svg_content") String svg_content){

        Floor floor = floorService.getFloorById(Long.valueOf(floorid));
        floor.setSvgContent(svg_content);
        floorService.update(floor);
        return "Saved successfully!";
    }

    @ResponseBody
    @RequestMapping(value = "api/design/save", method = RequestMethod.POST)
    public String saveFloor(@RequestParam("floorid") String floorid,
                            @RequestParam("svg_content") String svg_content,
                            @RequestParam("width") int width,
                            @RequestParam("height") int height
                            ){

        Floor floor = floorService.getFloorById(Long.valueOf(floorid));

        floor.setSvgContent(svg_content);
        floor.setWidth(width);
        floor.setHeight(height);
        floorService.update(floor);

        return "Saved successfully!";
    }

    @ResponseBody
    @RequestMapping(value = "api/user/details", method = RequestMethod.GET)
    public User getUserDetail(@RequestParam("id") String userid){
        User user = userService.getUserById(Long.valueOf(userid));
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "api/user/floor/update", method = RequestMethod.POST)
    public String updateUsersOnFloor(@RequestParam("floorid") Long floorid,
                                     @RequestParam("userid") Long userId){
        User user = userService.getUserById(userId);
        Floor floor = floorService.findById(floorid);
        user.setFloor(floor);
        userService.update(user);
        return "Saved successfully!";

    }



    /*

//    @ResponseBody
//    @RequestMapping(value = "api/user/get", method = RequestMethod.GET)
//    public User getUserDetail(@RequestParam("userid") String userid){
//
//        User user = userService.getUserById(Long.valueOf(userid));
//        return user;
//
//    }

    @ResponseBody
    @RequestMapping(value = "api/groups-by-floor", method = RequestMethod.GET)
    public User getGroupsByFloorId(@RequestParam("floorid") Long floorid){
       // List<Group> groupList = userService.getGroupsByFloorId(floorid);
        //new ArrayList<>();
        //SELECT DISTINCT user_groups.name, user_groups.color
        //FROM user
        //LEFT JOIN user_groups ON (user.group_id = user_groups.id)
        //WHERE floor_id =1
        //User user = userService.getUserById(Long.valueOf(userid));
        return null;

    }










    //user




    @ResponseBody
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> allusers(){

        List<User> users = userService.findAll();

        return users;
    }



*/
}
