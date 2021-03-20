package com.usermodule.service;

import com.usermodule.model.token.ConfirmationToken;
import com.usermodule.model.user.Role;
import com.usermodule.model.user.User;
import com.usermodule.repository.UserRepository;
import com.usermodule.utility.exception.CustomException;
//import com.usermodule.utility.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private  static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signUpUser(User user){

        //to check is user is present or not (return type boolean)
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if(userExists){
            //TODO Check attributes(firstname,lastname etc are same
            //TODO and if email not confirmed then confirmation email.

            throw new CustomException("Email already taken", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        //Generating random number for Email verification token
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    //for enabling user based on email
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    public List<Role> signin(String email, String password) {
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
             List<Role> roles = Collections.singletonList(userRepository.findByEmail(email).get().getRole());
  //          return jwtTokenProvider.createToken(email, roles);
            return roles;
        }
        catch (AuthenticationException e){
            throw new CustomException("Invalid Username/Password supplied",HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }
}
