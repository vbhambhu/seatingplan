package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.ac.ox.kir.seatingplan.entities.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

   // @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")

//    @Query("SELECT g FROM user_groups")
//    public List<Group> find(@Param("lastName") Long floorId);


}