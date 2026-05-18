package com.example.rentapp.service;

import com.example.rentapp.dto.LoginRequest;
import com.example.rentapp.dto.RegisterRequest;
import com.example.rentapp.model.User;
import com.example.rentapp.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepo userRepository;

    public AuthService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest request) {
        // Validare: Email unic
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email-ul este deja utilizat!");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        // Simulare hashing pentru Faza 1 (înlocuiește cu BCrypt mai târziu)
        user.setPasswordHash("HASHED_" + request.getPassword()); 
        user.setPhone(request.getPhone());
        user.setRole(request.getRole().toUpperCase());

        userRepository.save(user);
        return "Utilizator înregistrat cu succes!";
    }

    public User login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String checkHash = "HASHED_" + request.getPassword();
            if (user.getPasswordHash().equals(checkHash)) {
                return user; // Returnăm userul ca sesiune/confirmare
            }
        }
        throw new RuntimeException("Credențiale invalide!");
    }
}