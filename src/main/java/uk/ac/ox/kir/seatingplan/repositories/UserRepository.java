package uk.ac.ox.kir.seatingplan.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ox.kir.seatingplan.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByLoginToken(String token);

    User findByEmailAndIdNot(String email, Long id);

    User findByUsernameAndIdNot(String username, Long id);

    List<User> findByUsernameContainingOrFirstNameContainingOrLastNameContainingOrEmailContaining(String s1,
                                                                                                  String s2,
                                                                                                  String s3,
                                                                                                  String s4);

}