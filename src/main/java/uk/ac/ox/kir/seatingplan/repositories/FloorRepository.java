package uk.ac.ox.kir.seatingplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.Floor;

public interface FloorRepository extends JpaRepository<Floor, Long> {

}