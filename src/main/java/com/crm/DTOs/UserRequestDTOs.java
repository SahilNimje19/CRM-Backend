package com.crm.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTOs {
    private String username;
    private String password;
    private String email;

}
