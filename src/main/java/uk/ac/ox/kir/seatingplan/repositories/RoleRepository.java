package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {


}