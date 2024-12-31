package com.example.demo.dto;

import lombok.Data;

@Data
public class UserCredential {

    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    
}
