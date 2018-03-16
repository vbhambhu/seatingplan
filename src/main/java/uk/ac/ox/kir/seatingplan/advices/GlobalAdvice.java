package uk.ac.ox.kir.seatingplan.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.ox.kir.seatingplan.services.FloorService;

@ControllerAdvice
public class GlobalAdvice {

    @Value("${app.version}")
    private String applicationVersion;

    @Autowired
    FloorService floorService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("globalFloorList", floorService.findAll());
    }

    @ModelAttribute("applicationVersion")
    public String getApplicationVersion() {
        return applicationVersion;
    }

}
