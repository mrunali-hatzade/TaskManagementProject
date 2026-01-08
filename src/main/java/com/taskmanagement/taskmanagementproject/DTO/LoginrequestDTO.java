package com.taskmanagement.taskmanagementproject.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginrequestDTO {
    private String userofficialemail;
    private String password;

}
