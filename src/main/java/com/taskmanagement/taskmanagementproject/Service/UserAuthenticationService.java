package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.DTO.AuthResponseDTO;
import com.taskmanagement.taskmanagementproject.DTO.LoginrequestDTO;
import com.taskmanagement.taskmanagementproject.DTO.RegisterRequestDTO;
import com.taskmanagement.taskmanagementproject.Entity.UserAuthentication;
import com.taskmanagement.taskmanagementproject.Repository.UserAuthenticationRepository;
import com.taskmanagement.taskmanagementproject.Security.JWTTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class UserAuthenticationService {
    @Autowired
    private UserAuthenticationRepository userAuthRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    public String register(RegisterRequestDTO register) {

        if(userAuthRepo.findByUserOfficialEmail(register.userofficialemail).isPresent() ){
            throw new RuntimeException("USer Already Exists");
        }

        UserAuthentication user=new UserAuthentication();

        user.setUsername(register.username);
        user.setUserofficialemail(register.userofficialemail);
        user.setPassword(passwordEncoder.encode(register.password));
        user.setRole(register.role);
        userAuthRepo.save(user);

        return "User registered successfully";
    }

    public AuthResponseDTO login(LoginrequestDTO login){

        UserAuthentication user = userAuthRepo
                .findByUserOfficialEmail(login.getUserofficialemail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponseDTO(token,"Token got generated ");


    }

}
