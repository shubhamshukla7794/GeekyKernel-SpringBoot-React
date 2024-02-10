package com.shubham.geekykernel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.dao.UserRepository;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users") 
	public User createUser(@RequestBody User user) {
		
		User savedUser = userService.registerUser(user);
		
		return savedUser;
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		List<User> users = userRepository.findAll();
		
		return users;
	}
	
	@GetMapping("/users/{userId}")
	public User getUserById(@PathVariable("userId") Integer id) {
				
		return userService.findUserById(id);
	}
	
	@PutMapping("/users/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable Integer userId) {
		
		Optional<User> user1 = userRepository.findById(userId);
		
		if (user1.isEmpty()) {
			throw new RuntimeException("User with id " + userId + " not found");
		}
		
		
		User oldUser = user1.get();
		
		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		} 
		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}
		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}
		
		User updatedUser = userRepository.save(oldUser);
		
		return updatedUser;
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable Integer userId) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty()) {
			throw new RuntimeException("User with id " + userId + " not found");
		}
		
		userRepository.delete(user.get());
		
		return "User deleted successfully with id " + userId;
	}
}
