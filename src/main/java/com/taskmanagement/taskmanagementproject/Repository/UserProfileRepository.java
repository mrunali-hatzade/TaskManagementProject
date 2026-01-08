package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository


public interface UserProfileRepository extends JpaRepository<UserProfile,Long > {
    Optional<UserProfile>findUserProfileByUserofficialEmail(String userofficialemail);
    List<UserProfile>findUserByDesignation(String designation);
    List<UserProfile>findUserByDepartment(String department);



}
