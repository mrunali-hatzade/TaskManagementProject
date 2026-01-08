package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.DTO.UserProfileDTO;
import com.taskmanagement.taskmanagementproject.Entity.UserProfile;
import com.taskmanagement.taskmanagementproject.Repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepo;

    public UserProfileDTO updateUserProfile(UserProfileDTO dto){

        UserProfile user = userProfileRepo.findUserProfileByUserofficialEmail(dto.userofficialemail)
                .orElseThrow(()-> new RuntimeException("UserProfile Not Found"));


        UserProfile profile = new UserProfile();
        profile.setUsername(dto.getUsername());
        profile.setUserofficialemail(dto.userofficialemail);
        profile.setDepartment(dto.department);
        profile.setDesignation(dto.designation);
        profile.setOrganizationName(dto.organizationName);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setActive(true);

        userProfileRepo.save(profile);
        return toDTO(profile);
    }

    public List<UserProfileDTO> getAllUserProfile(){
        return userProfileRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public UserProfileDTO getProfileByemail(String userofficialemail){
        UserProfile user = userProfileRepo.findUserProfileByUserofficialEmail(userofficialemail).orElseThrow(()->new RuntimeException("UserProfile Not Found"));
        return toDTO(user);
    }

    public List<UserProfileDTO>getUserByDepartment(String department){
        return userProfileRepo.findUserByDepartment(department).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<UserProfileDTO>getUserByDesignation(String designation){
        return userProfileRepo.findUserByDesignation(designation).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UserProfileDTO toDTO(UserProfile user){
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername(user.getUsername());
        dto.setUserofficialemail(user.getUserofficialemail());
        dto.setDepartment(user.getDepartment());
        dto.setDesignation(user.getDesignation());
        dto.setOrganizationName(user.getOrganizationName());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setActive(user.isActive());
        return dto;


    }
}
