package com.financialapp.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String email;
    private Boolean appAdmin;
    private Integer points;
}
