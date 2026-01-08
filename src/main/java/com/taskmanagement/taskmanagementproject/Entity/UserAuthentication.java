package com.taskmanagement.taskmanagementproject.Entity;

import com.taskmanagement.taskmanagementproject.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
//import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_Auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String userofficialemail;

    private String password;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean active = true;
    private LocalDateTime creationdate = LocalDateTime.now();

    /*public UserAuthentication(){} ///NoArgsConstructor

    public UserAuthentication(Long id, String username, String userofficialemail, String password, Role role){
        this.id = id;
        this.username = username;
        this.userofficialemail = userofficialemail;
        this.password = password;
        this.role = role;
    }*/

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserofficialemail() {
        return userofficialemail;
    }

    public void setUserofficialemail(String userofficialemail) {
        this.userofficialemail = userofficialemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
