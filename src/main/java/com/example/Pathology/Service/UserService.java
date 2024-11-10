package com.example.Pathology.Service;

import com.example.Pathology.Entity.Role;
import com.example.Pathology.Entity.User;
import com.example.Pathology.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;



    private final List<String> adminEmails = Arrays.asList("admin1@gmail.com", "admin2@gmail.com");

    public User registerUser(User user) {

        if (adminEmails.contains(user.getUsername())) {
            user.setRole(Role.ADMIN); // Assign ADMIN role for certain emails
        } else {
            user.setRole(Role.USER);  // Default role is USER
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }


    public String verify(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated())return jwtService.generateToken(user.getUsername());
        else return "Login Failed";
    }


//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, User updatedUser) {

        User existingUser = getUserById(userId);
        if (existingUser != null) {

            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
