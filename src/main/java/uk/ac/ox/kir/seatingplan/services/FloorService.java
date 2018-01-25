package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.repositories.FloorRepository;

import java.util.List;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;

    public void save(Floor floor) {
        floorRepository.save(floor);
    }

    public List<Floor> findAll() {
        return floorRepository.findAll();
    }

    public Floor getFloorById(Long id) {

        return floorRepository.findOne(id);

    }
}
