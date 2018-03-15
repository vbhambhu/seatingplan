package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.repositories.FloorRepository;

import java.util.Date;
import java.util.List;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;



    public void create(Floor floor) {


        floor.setCreatedAt(new Date());
        floor.setDefault(false);
        floorRepository.save(floor);





        /*

        FloorVersion floorVersion = new FloorVersion();
        floorVersion.setSvgContent(floor.getSvgContent());
        floorVersion.setCreatedAt(new Date());

        if(floor.getId() == null){ // new floor
            floorVersion.setFloorId((long) 0);
        } else{
            floorVersion.setFloorId(floor.getId());
        }

        floorVersionRepository.save(floorVersion);

        //Save floor


        floor.setCreatedAt(new Date());
        floor.setVersion((long) 1);
       // floorRepository.save(floor);

        System.out.println("============");
        System.out.println(floor.getId());




//        if(floor.getId() == null){
//            floorVersion.setFloorId((long) 1);
//        } else{
//            floorVersion.setFloorId(floor.getId());
//        }
//
//
//        floorVersionRepository.save(floorVersion);




//        Long version = floorVersionRepository.countByFloorId(floor.getId());
//
//        floor.setVersion(version);
//
//
//        floor.setCreatedAt(new Date());
//        floorRepository.save(floor);

*/
    }

    public List<Floor> findAll() {
        return floorRepository.findAll();
    }

    public Floor getFloorById(Long id) {

        return floorRepository.findOne(id);

    }


    public Long getDefaultFloorId() {

        List<Floor> floors = floorRepository.findByIsDefault(true);

        if(floors.size() == 1){
           return  floors.get(0).getId();
        } else{
            return null;
        }

    }

    public void update(Floor floor) {
        floorRepository.save(floor);
    }

    public void delete(Long id) {

        Floor floor = floorRepository.findOne(id);

        if(floor == null) return;

        floorRepository.delete(floor);
    }

//    public int getCountById(Long aLong) {
//
//        Long countByName(String name);
//    }
}
