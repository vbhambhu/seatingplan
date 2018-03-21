package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ox.kir.seatingplan.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByName(String roleName);

    Role findByNameAndIdNot(String name, Long id);


}