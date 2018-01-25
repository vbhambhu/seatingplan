package uk.ac.ox.kir.seatingplan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {


    @ResponseBody
    @RequestMapping(value = "api/design/save", method = RequestMethod.POST)
    public String saveFloor(){

        return "saved";

    }


}
