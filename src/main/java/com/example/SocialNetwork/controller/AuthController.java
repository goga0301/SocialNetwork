package com.example.SocialNetwork.controller;


import com.example.SocialNetwork.dto.AuthenticationResponse;
import com.example.SocialNetwork.dto.LoginRequest;
import com.example.SocialNetwork.dto.RegisterRequest;
import com.example.SocialNetwork.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registered Succesfully", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verification(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated!!",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);

    }

}
