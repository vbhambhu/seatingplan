package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;



    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository.findOne(id);

    }

    public void create(User user) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String uuid = UUID.randomUUID().toString().replace("-", "");

        user.setCreatedAt(new Date());

        if(user.getPassword() == null){
            user.setPassword(passwordEncoder.encode(uuid));
        }

        userRepository.save(user);
    }

    public void update(User user) {

        user.setUpdatedAt(new Date());
        userRepository.save(user);

    }

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long gid) {
        return groupRepository.findOne(gid);
    }

    public boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return (user == null) ? true : false;
    }

    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return (user == null) ? true : false;
    }


    public boolean duplicateUsername(Long id, String username) {

        User user = userRepository.findByUsernameAndIdNot(username, id);
        return (user == null) ? true : false;

    }

    public boolean duplicateEmail(Long id, String email) {

        User user = userRepository.findByEmailAndIdNot(email, id);
        return (user == null) ? true : false;

    }


}
