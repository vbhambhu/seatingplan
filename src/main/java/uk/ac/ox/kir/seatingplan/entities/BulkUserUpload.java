package uk.ac.ox.kir.seatingplan.entities;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class BulkUserUpload {

    @Size(min=4, max=30, message = "Username field must be between 4 and 30 characters.")
    private String username;

    @Size(min=2, max=30, message = "First name field must be between 2 and 30 characters.")
    private String firstName;

    @Size(min=2, max=30, message = "Last name field must be between 2 and 30 characters.")
    private String lastName;

    @NotEmpty(message = "Email field is required.")
    @Email(message = "Email field is not an valid email address.")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start Date field is required.")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End Date field is required.")
    private Date endDate;

    private String imageUrl;

    private boolean enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
