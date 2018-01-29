package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository.findOne(id);

    }
}
