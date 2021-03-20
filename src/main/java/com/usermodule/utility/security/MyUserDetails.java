package com.usermodule.utility.security;

import com.usermodule.model.user.User;
import com.usermodule.repository.UserRepository;
import com.usermodule.utility.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> user = userRepository.findByEmail(username);

        if(!user.isPresent()){
            throw new UsernameNotFoundException("Email '"+username+"' not found");
        }

        return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(user.get().getPassword())//
        .authorities(user.get().getRole())//
        .accountExpired(user.get().isAccountNonExpired())//
        .accountLocked(user.get().isAccountNonLocked())//
        .credentialsExpired(user.get().isCredentialsNonExpired())//
        .disabled(user.get().getEnabled())//
        .build();
    }
}
