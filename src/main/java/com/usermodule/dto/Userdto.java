package com.usermodule.dto;

import com.usermodule.model.user.Gender;
import com.usermodule.model.user.Role;
import com.usermodule.model.user.Title;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Userdto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Title title;
    private String number;
    //private String fileName;
}
