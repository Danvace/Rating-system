package com.danylo.braslavets.studentRating.Ratingsystem.auth;

import com.danylo.braslavets.studentRating.Ratingsystem.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotEmpty(message = "name should not be empty")
    private String name;

    @Email(message = "bad format")
    private String email;

    private String password;

    private Role role;


}
