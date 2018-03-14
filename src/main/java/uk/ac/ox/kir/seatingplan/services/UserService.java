package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import uk.ac.ox.kir.seatingplan.entities.ActionStatus;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.UserRepository;
import uk.ac.ox.kir.seatingplan.utils.SiteHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EmailService emailService;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Value("${app.name}")
    private String appName;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository.findOne(id);

    }

    public void create(User user) {

        user.setCreatedAt(new Date());
        user.setLoginToken(UUID.randomUUID().toString());
        user.setUsername(user.getUsername().toLowerCase());
        user.setFirstName(SiteHelper.ucword(user.getFirstName()));
        user.setLastName(SiteHelper.ucword(user.getLastName()));
        userRepository.save(user);

        //send email to activate account.
        //String link = baseUrl+"password/update?token="+user.getLoginToken();
        //Context context = new Context();
        //context.setVariable("link", link);

        //emailService.sendHtml(user.getEmail(),siteName+" - Your temporary login link","login",context);

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


    public List<Group> getGroupsByFloorId(Long floorid) {


        //return groupRepository.find(floorid);

        return null;

    }

    public boolean checkLoginToken(String token) {
        return (userRepository.findByLoginToken(token) == null) ? false : true;
    }

    public User getUserByLoginToken(String token) {
        return userRepository.findByLoginToken(token);
    }

    public void activateAccountByToke(String token, String password) {

        User user = getUserByLoginToken(token);

        if(user != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            user.setLoginToken(null);
            update(user);
        }
    }

    public boolean isValidEmailAddress(String email) {
        return (userRepository.findByEmail(email) == null) ? false : true;
    }

    public ActionStatus sendPasswordRestLink(String email) {

        User user = userRepository.findByEmail(email);

        if(user != null){

            //Update login token
            user.setLoginToken(UUID.randomUUID().toString());
            userRepository.save(user);

            //send reset email
            String link = baseUrl+"password/update?token="+user.getLoginToken();
            Context context = new Context();
            context.setVariable("link", link);
            emailService.sendHtml(user.getEmail(),appName+" - Password Reset","password_reset",context);
        }
        return null;
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

//   // public List<Role> getAllRoles() {
//        return userRepository.getAllRoles();
//    }
}