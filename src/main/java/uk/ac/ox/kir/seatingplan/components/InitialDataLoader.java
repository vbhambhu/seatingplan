package uk.ac.ox.kir.seatingplan.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.ac.ox.kir.seatingplan.entities.Floor;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.RoleRepository;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Role admin_role = createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Group group = createGroupIfNotFound("Default Group");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        User user = userRepository.findByUsername("spadmin");

        if (user == null) {
            user.setUsername("spadmin");
            user.setFirstName("Super");
            user.setLastName("Admin");
            user.setPassword(passwordEncoder.encode("spadmin"));
            user.setEmail("test@test.com");
            user.setRoles(Arrays.asList(admin_role));
            user.setGroups(Arrays.asList(group));
            user.setEnabled(true);
            userRepository.save(user);
        }


    }


    private Group createGroupIfNotFound(String groupName) {

        Group group = groupRepository.findByName(groupName);

        if (group == null) {
            group = new Group();
            group.setName(groupName);
            group.setColor("F06");
            groupRepository.save(group);
        }
        return group;

    }


    private Role createRoleIfNotFound(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}
