package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.refreshtoken.TokenRefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refreshtoken(@RequestBody TokenRefreshRequest request){
        return ResponseEntity.ok(service.NewJwtToken(request));
    }



}
