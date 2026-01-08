package com.taskmanagement.taskmanagementproject.Controller;

import com.taskmanagement.taskmanagementproject.DTO.AuthResponseDTO;
import com.taskmanagement.taskmanagementproject.DTO.LoginrequestDTO;
import com.taskmanagement.taskmanagementproject.DTO.RegisterRequestDTO;
import com.taskmanagement.taskmanagementproject.Service.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class UserAuthenticateController {
    @Autowired
    private UserAuthenticationService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO dto){

        authService.register(dto);
        return ResponseEntity.ok("User register Successful");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO>login(@RequestBody LoginrequestDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }


}
