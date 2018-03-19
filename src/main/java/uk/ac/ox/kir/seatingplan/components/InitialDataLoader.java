package uk.ac.ox.kir.seatingplan.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.FloorRepository;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.RoleRepository;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Value("${app.admin.email}")
    private String email;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_NO_ACCESS");

        createGroupIfNotFound("Default Group");


        User userFromDB = userRepository.findByUsername("admin");

        if(userFromDB != null){
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Group adminGroup = groupRepository.findByName("Default Group");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setFirstName("Super");
        user.setLastName("Admin");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEnabled(true);
        user.setLoginToken(null);
        user.setStartDate(new Date());
        user.setEndDate(new Date());
        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        user.setEmail(email);
        user.setRoles(Arrays.asList(adminRole));
        user.setGroups(Arrays.asList(adminGroup));

        userRepository.save(user);



        createFloorIfNotFound();

    }

    private void createFloorIfNotFound() {

        Floor floor = floorRepository.findOne(Long.valueOf(1));

        if (floor == null) {
            floor = new Floor();
            floor.setName("Default floor");
            floor.setCreatedAt(new Date());
            floorRepository.save(floor);
        }

    }

    private Group createGroupIfNotFound(String name) {

        Group group = groupRepository.findByName(name);

        if (group == null) {
            group = new Group();
            group.setName(name);
            group.setColor("F06");
            groupRepository.save(group);
        }
        return group;
    }


    private Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }


}
