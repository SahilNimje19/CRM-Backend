package com.crm.DTOs;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTOs {
    private String userName;

    // 👇 YOU MUST ADD THIS FIELD!
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;


}