package com.example.Pathology.Service;

import com.example.Pathology.Entity.Role;
import com.example.Pathology.Entity.User;
import com.example.Pathology.Entity.UserPrincipal;
import com.example.Pathology.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found with username : " + username);
        }
        System.out.println("User logged in with email: " + user.getUsername() + " and role: " + user.getRole());
        return new UserPrincipal(user, getAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

}
