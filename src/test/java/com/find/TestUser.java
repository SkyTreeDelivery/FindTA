package com.find;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {
    private Long id;
    private String username;
    private String password;
    private Integer gender;
    private Integer age;
    private Date birthday;
}
