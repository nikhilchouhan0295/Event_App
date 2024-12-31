package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AuthRequest;
import com.demo.dto.ResponseDto;
import com.demo.entity.UserCredential;
import com.demo.repository.UserCredentialRepository;
import com.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private UserCredentialRepository userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    

    @PostMapping("/register")
    public String addNewUser(@RequestBody @Valid UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseDto getToken(@RequestBody AuthRequest authRequest) {
    	System.out.println("yes .."+authRequest.getUsername()+"  "+authRequest.getPassword());
    	Authentication authenticate = authenticationManager
    			.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    	System.out.println(authenticate.isAuthenticated()+""+authenticate.getName());
      
    	if (authenticate.isAuthenticated()) {
    		
        	UserCredential user = userRepo.findByUsername(authRequest.getUsername()).get();
        	String token=service.generateToken(user.getId()+"",user.getRole());
        	
        	ResponseDto resDto=new ResponseDto();
        	resDto.setToken(token);
        	resDto.setRole(user.getRole());
        	return resDto;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    
    @GetMapping("/getUserDetailsbyUserId/{userId}")
    public ResponseEntity<?> getUserDetailsbyUserId(@PathVariable int userId) {
    	UserCredential userDetails =  service.getUserDetailsbyUserId(userId);
    	return new ResponseEntity<>(userDetails,HttpStatus.OK);
    }
    
    
    
}
