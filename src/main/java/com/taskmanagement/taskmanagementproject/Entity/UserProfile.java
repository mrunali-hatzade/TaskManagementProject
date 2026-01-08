package com.taskmanagement.taskmanagementproject.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "userProfiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder


public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String userofficialemail;

    private String designation;
    private String department;
    private String organizationName;

    private LocalDateTime createdAt;

    private boolean active=true;


}
