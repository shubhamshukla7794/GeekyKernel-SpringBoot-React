package com.shubham.geekykernel.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.config.JwtProvider;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.request.LoginRequest;
import com.shubham.geekykernel.response.AuthResponse;
import com.shubham.geekykernel.service.CustomUserDetailsService;
import com.shubham.geekykernel.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private CustomUserDetailsService customUserDetailsService;

	public AuthController(UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@PostMapping("/signup") 
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		
		User isUser = userService.findUserByEmail(user.getEmail());
		
		if (isUser != null) {
			throw new Exception("This email is already in use for another account!!!");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userService.registerUser(user);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse response = new AuthResponse(token, "Registered Successfully");
		
		return response;
	}
	
	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse response = new AuthResponse(token, "Logged in Successfully");
		
		return response;
	}

	private Authentication authenticate(String email, String password) {
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
		
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Email!!!");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password!!!");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
}
