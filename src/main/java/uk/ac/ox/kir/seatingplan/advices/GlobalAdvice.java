package uk.ac.ox.kir.seatingplan.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.ox.kir.seatingplan.services.FloorService;

@ControllerAdvice
public class GlobalAdvice {


    @Autowired
    FloorService floorService;

    /*
    * Controller advice to set floors attribute
    * This will enable globalFloorList accessible to all pages of app
    * */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("globalFloorList", floorService.findAll());
    }

}
