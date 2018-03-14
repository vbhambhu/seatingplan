package uk.ac.ox.kir.seatingplan.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ox.kir.seatingplan.entities.ActionStatus;
import uk.ac.ox.kir.seatingplan.entities.PasswordResetForm;
import uk.ac.ox.kir.seatingplan.entities.PasswordUpdateForm;
import uk.ac.ox.kir.seatingplan.entities.User;
import uk.ac.ox.kir.seatingplan.services.UserService;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "account/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(User user){
        return "account/register";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public String passwordReset(PasswordResetForm passwordResetForm){
        return "account/password_reset";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String validateAndSendPasswordRestLink(@RequestParam(name = "email") String email,
                                                  @Valid PasswordResetForm passwordResetForm,
                                                  BindingResult bindingResult,
                                                  Model model,
                                                  RedirectAttributes redirAttrs){

        if(bindingResult.hasErrors()){
            return "account/password_reset";
        } else if(!userService.isValidEmailAddress(passwordResetForm.getEmail())){
            bindingResult.rejectValue("email", "email", "Invalid email address. No account exists with this email.");
            return "account/password_reset";
        }


        ActionStatus actionStatus = userService.sendPasswordRestLink(email);

        redirAttrs.addFlashAttribute("successMsg", "An email with instructions to reset your password is sent. Please check your email and follow the instructions.");
        return "redirect:/password/reset";
    }


    @RequestMapping(value = "/admin/user/create", method = RequestMethod.GET)
    public String createUser(User user){
        return "admin/users/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String validateAndSaveUser(@Valid User user, BindingResult bindingResult){

        if(!userService.usernameExists(user.getUsername())){
            bindingResult.rejectValue("username", "username", "Username already exists.");
        } else if(!userService.emailExists(user.getEmail())){
            bindingResult.rejectValue("email", "email", "Email address already exists");
        }

        if(bindingResult.hasErrors()){
            return "admin/users/create";
        }

        userService.create(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.GET)
    public String templogin(@RequestParam(name = "token", required = false) String token,
                            Model model, PasswordUpdateForm passwordUpdateForm,
                            RedirectAttributes redirectAttributes){

        if(token == null){
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid or expired token.");
            return "redirect:/login";
        }

        if(!userService.checkLoginToken(token)){
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid or expired token.");
            return "redirect:/login";
        }

        model.addAttribute("token", token);
        return "account/password_update";
    }

    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    public String activateAccount(@RequestParam(name = "token", required = true) String token,
                                  @Valid PasswordUpdateForm passwordUpdateForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttrs){


        if(!userService.checkLoginToken(token)){
            return "account/invalid_token";
        }

        model.addAttribute("token", token);

        if(!passwordUpdateForm.getPassword().equals(passwordUpdateForm.getConfirmPassword())){
            bindingResult.rejectValue("password","passwordResetForm.password", "New Password and Verify New Password did not match.");
        }

        if(bindingResult.hasErrors()){
            return "account/password_update";
        }

        //reset password and activate account
        userService.activateAccountByToke(token, passwordUpdateForm.getPassword());

        redirectAttrs.addFlashAttribute("successMsg", "Password is updated successfully!. Please login with new password.");
        return "redirect:/login";

    }
}
