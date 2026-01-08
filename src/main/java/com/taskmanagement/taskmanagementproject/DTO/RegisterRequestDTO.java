package com.taskmanagement.taskmanagementproject.DTO;

import com.taskmanagement.taskmanagementproject.Enum.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequestDTO {
    public String username;
    public String userofficialemail;
    public String password;
    public Role role;

}
