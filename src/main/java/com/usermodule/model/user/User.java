package com.usermodule.model.user;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Email(message = "Email Should be Valid")
    @NotNull(message = "Email can't be null")
    private String email;

    @NotNull(message = "Password can't be null")
    private String password;

    @NotNull(message = "FirstName cant't be null")
    private String firstName;

    @NotNull(message = "LastName cant't be null")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String number;

    private Boolean locked=false;

    private Boolean enabled=false;

 //   private String fileName;

    public User(@Email(message = "Email Should be Valid") @NotNull(message = "Email can't be null") String email, @NotNull(message = "Password can't be null") String password, @NotNull(message = "FirstName cant't be null") String firstName, @NotNull(message = "LastName cant't be null") String lastName, Gender gender, Title title, Role role, String number) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.title = title;
        this.role = role;
        this.number = number;
  //      this.fileName = fileName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
