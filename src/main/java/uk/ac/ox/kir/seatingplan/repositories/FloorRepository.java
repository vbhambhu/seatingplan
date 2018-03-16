package uk.ac.ox.kir.seatingplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.Floor;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Long> {


    List<Floor> findByIsDefault(boolean isDefault);

    Floor findTopByOrderByIdAsc();
}