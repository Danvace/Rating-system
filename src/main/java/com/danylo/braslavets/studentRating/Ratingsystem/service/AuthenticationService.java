package com.danylo.braslavets.studentRating.Ratingsystem.service;

import com.danylo.braslavets.studentRating.Ratingsystem.auth.AuthenticationRequest;
import com.danylo.braslavets.studentRating.Ratingsystem.auth.AuthenticationResponse;
import com.danylo.braslavets.studentRating.Ratingsystem.auth.RegisterRequest;
import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentAlreadyExistsException;
import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Role;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.StudentRepository;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.TokenRepository;
import com.danylo.braslavets.studentRating.Ratingsystem.token.Token;
import com.danylo.braslavets.studentRating.Ratingsystem.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final StudentRepository studentRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<Student> studentOptional = studentRepository.findByEmail(request.getEmail());

        if (studentOptional.isPresent()) {
            throw new StudentAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        Student student = Student
                .builder()
                .studentName(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        Student savedStudent = studentRepository.save(student);
        String jwtToken = jwtService.generateToken(savedStudent);
        saveUserToken(savedStudent, jwtToken);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();

    }

    private void saveUserToken(Student student, String jwtToken) {

        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .student(student)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new StudentNotFoundException("Student not found with email: " + request.getEmail())
                );

        String jwtToken = jwtService.generateToken(student);
        revokeAllUserTokens(student);
        saveUserToken(student, jwtToken);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(Student student) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(student.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
