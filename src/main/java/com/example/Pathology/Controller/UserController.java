package com.example.Pathology.Controller;

import com.example.Pathology.Entity.User;
import com.example.Pathology.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);

    }


//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User loginUser) {
//        User user = userService.findByEmail(loginUser.getEmail());
//
//        if (user != null) {
//            System.out.println("User found: " + user.getEmail());
//
//            if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
//                return ResponseEntity.ok("Login successful");
//            } else {
//                System.out.println("Password mismatch for user: " + user.getEmail());
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//            }
//        } else {
//            System.out.println("User not found with email: " + loginUser.getEmail());
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//    }


//    @GetMapping("/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
//        User user = userService.findByEmail(email);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
