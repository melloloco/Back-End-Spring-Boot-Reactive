package com.example.demo.application.userapplication;
import com.example.demo.domain.userdomain.Rol;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

public @Getter @Setter class UpdateDTO{
    @NotBlank @Size(min=8, max=16)
    private String firstname;
    @NotBlank @Size(min=8, max=16)
    private String lastname;
    @NotBlank @Size(min=8, max=16) @Email
    private String email;
    @NotBlank @Size(min=8, max=16)
    private String password;
    private String NewPassword;
    private Rol rol = Rol.USER;
    private String type;
}