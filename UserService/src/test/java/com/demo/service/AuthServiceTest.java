package com.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.entity.UserCredential;
import com.demo.repository.UserCredentialRepository;

@SpringBootTest
class AuthServiceTest {

	@Autowired private AuthService authService;
	
	@MockBean private UserCredentialRepository credentialRepository;
	
	@BeforeEach
	void setUpOfMockito() {
		Optional<UserCredential> mockUser = Optional.of(new UserCredential(1, "av12", "a@gmail.com", "1234", "user"));
		Mockito.when(credentialRepository.findById(1)).thenReturn(mockUser);
		Mockito.when(credentialRepository.save(mockUser.get())).thenReturn(mockUser.get());
	}

	@Test
	void testSaveUser() {
		UserCredential user = new UserCredential(1, "av12", "a@gmail.com", "1234", "user");
		assertEquals("user added to the system", authService.saveUser(user)); 
	}

	@Test
	void testGetUserDetailsbyUserId() {
		UserCredential user = new UserCredential(1, "av12", "a@gmail.com", "1234", "user");
		UserCredential resultUser = authService.getUserDetailsbyUserId(1);
		assertEquals(user, resultUser);
	}

}
