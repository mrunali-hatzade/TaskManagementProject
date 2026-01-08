package com.taskmanagement.taskmanagementproject.Controller;

import com.taskmanagement.taskmanagementproject.DTO.UserProfileDTO;
import com.taskmanagement.taskmanagementproject.Entity.UserProfile;
import com.taskmanagement.taskmanagementproject.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userProfile")

public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PutMapping("/{email}")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO dto) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(dto));
    }
    @GetMapping("/{all}")
    public ResponseEntity<List<UserProfileDTO>> getAllUser() {
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }
    @GetMapping("/{email}")
    public ResponseEntity<UserProfileDTO> getUserByEmail(@PathVariable String userOfficialEmail) {
        return ResponseEntity.ok(userProfileService.getProfileByemail(userOfficialEmail));
    }
    @GetMapping("/{designation}")
    public ResponseEntity<List<UserProfileDTO>> getUserByDesignation(@PathVariable String designation) {
        return ResponseEntity.ok(userProfileService.getUserByDesignation(designation));
    }
    @GetMapping("/{department}")
    public ResponseEntity<List<UserProfileDTO>> getUserByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(userProfileService.getUserByDepartment(department));
    }


}
