package com.crm.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentResponseDTO {
    private long id;
    private String name;
    private String desc;
}
