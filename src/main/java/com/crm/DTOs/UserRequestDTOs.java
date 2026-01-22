package com.crm.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTOs {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
