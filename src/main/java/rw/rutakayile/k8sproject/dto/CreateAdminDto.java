package rw.rutakayile.k8sproject.dto;

import lombok.Data;
import rw.rutakayile.k8sproject.annotation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateAdminDto {

    @NotNull(message = "First name is empty")
    private String firstName;

    @NotNull(message = "Last name is empty")
    private String lastName;

    @NotNull(message = "Email is empty")
    @Pattern(regexp="^(.+)@(.+)$",message="Invalid email")
    private String email;

    @ValidPassword(message = "Password is invalid; password must be between 8-30 characters, must have at least 1 upper cased, 1 lower cased letter, 1 number, 1 special character & no white space")
    private String password;
}
