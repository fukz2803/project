package com.foodei.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email cannot blank")
    @Email(message = "Invalid email")
    private String email;

    @Size(min=6, max=20, message="Password length must between 6 and 20 characters")
    private String password;
}
