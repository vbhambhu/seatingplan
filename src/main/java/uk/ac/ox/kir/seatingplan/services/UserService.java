package uk.ac.ox.kir.seatingplan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import uk.ac.ox.kir.seatingplan.entities.*;
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
        user.setUpdatedAt(new Date());
        user.setLoginToken(UUID.randomUUID().toString());
        user.setUsername(user.getUsername().toLowerCase());
        user.setFirstName(SiteHelper.ucword(user.getFirstName()));
        user.setLastName(SiteHelper.ucword(user.getLastName()));
        userRepository.save(user);

        //TODO: send activation email

    }

    public void update(User user) {

        User user1 = userRepository.findOne(user.getId());

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());

        user1.setStartDate(user.getStartDate());
        user1.setEndDate(user.getEndDate());
        user1.setGroups(user.getGroups());
        user1.setEnabled(user.isEnabled());
        user1.setImageUrl(user.getImageUrl());

        user1.setComputerDetails(user.getComputerDetails());
        user1.setPhone(user.getPhone());
        user1.setNotes(user.getNotes());
        user1.setRoles(user.getRoles());
        user1.setUpdatedAt(new Date());

        userRepository.save(user1);
    }

    public boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return (user == null) ? true : false;
    }

    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return (user == null) ? true : false;
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

    public void createUser(User user) {


        if(user.isEnabled()){
            //send email to get a new password.
        }


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if(user.getPassword() == null){
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        }

        user.setUpdatedAt(new Date());
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> search(String q) {
        return userRepository.findByUsernameContainingOrFirstNameContainingOrLastNameContainingOrEmailContaining(q,q,q,q);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}