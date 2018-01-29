package uk.ac.ox.kir.seatingplan.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.ox.kir.seatingplan.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}