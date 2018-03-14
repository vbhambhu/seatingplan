package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.ac.ox.kir.seatingplan.entities.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByName(String group_name);

    Group findByNameAndIdNot(String name, Long id);

}