package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {


}