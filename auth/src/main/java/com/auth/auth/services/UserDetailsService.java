package com.auth.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.auth.auth.models.UserModel;
import com.auth.auth.repositorys.UserRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stu
        UserModel user = userRepository.findByemail(username).orElseThrow(() -> new UsernameNotFoundException("usuario" + username + "nao encontrado"));
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
        
    }


    
}
