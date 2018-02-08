package uk.ac.ox.kir.seatingplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.entities.FloorVersion;

public interface FloorVersionRepository extends JpaRepository<FloorVersion, Long> {


    Long countByFloorId(Long floorId);

}