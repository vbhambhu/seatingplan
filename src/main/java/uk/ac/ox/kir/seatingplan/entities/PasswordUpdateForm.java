package uk.ac.ox.kir.seatingplan.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class PasswordUpdateForm {

    @NotEmpty(message = "New Password field is required.")
    private String password;


    @NotEmpty(message = "Verify New Password field is required.")
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
