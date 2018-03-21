package uk.ac.ox.kir.seatingplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ox.kir.seatingplan.entities.Floor;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {


    Floor findTopByOrderByIdAsc();
}