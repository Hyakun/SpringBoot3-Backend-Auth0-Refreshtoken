package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.datamodels.ProfessorCode;
import com.example.demo.datamodels.ProfessorCodeRepository;
import com.example.demo.datamodels.StudentDetails;
import com.example.demo.datamodels.StudentDetailsRepository;
import com.example.demo.refreshtoken.RefreshToken;
import com.example.demo.refreshtoken.RefreshTokenRepository;
import com.example.demo.refreshtoken.TokenRefreshRequest;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final StudentDetailsRepository studentDetailsRepository;
    private final ProfessorCodeRepository professorCodeRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var userVerify = repository.findByEmail(request.getEmail());
        if(!userVerify.isEmpty()) {
            throw new RuntimeException("Utilizatorul exista deja!");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var codeVerify = professorCodeRepository.findByCode(request.getProfessorCode());

        if(!codeVerify.isEmpty()){
            user.setRole(Role.TEACHER);
            professorCodeRepository.deleteById(codeVerify.get().getId());
        }
        repository.save(user);
        var userDetails = StudentDetails.builder().user(user).build();
        studentDetailsRepository.save(userDetails);
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtRefreshToken);
        refreshToken.setUser(user);
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.save(refreshToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtRefreshToken);
        refreshToken.setUser(user);
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.save(refreshToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
    public AuthenticationResponse NewJwtToken(TokenRefreshRequest request) {
        if(jwtService.isTokenExpired(request.getRefreshToken())){
            throw new RuntimeException("Refresh token expirat");
        }
        refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow();

        String userEmail = jwtService.extractUsername(request.getRefreshToken());
        var user = repository.findByEmail(userEmail)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
