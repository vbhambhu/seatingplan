package uk.ac.ox.kir.seatingplan.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.RoleRepository;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private GroupRepository groupRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (alreadySetup) {
            return;
        }

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_NO_ACCESS");

        User userFromDB = userRepository.findByUsername("admin");

        if(userFromDB != null){
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setFirstName("Super");
        user.setLastName("Admin");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEnabled(true);
        user.setLoginToken(null);
        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        //TODO: This couuld be in config
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        user.setStartDate(new Date());
        user.setEndDate(new Date());
        userRepository.save(user);

        alreadySetup = true;

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
