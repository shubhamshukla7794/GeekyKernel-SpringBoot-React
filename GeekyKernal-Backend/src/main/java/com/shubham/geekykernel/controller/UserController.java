package com.shubham.geekykernel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public User getUserById(@PathVariable("userId") UUID id) {
				
		return userService.findUserById(id);
	}
	
	@PutMapping("/users/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable UUID userId) {
		
		User updatedUser = userService.updateUser(user, userId);
		
		return updatedUser;
	}
	
	@PutMapping("/users/follow/{userId1}/{userId2}")
	public User followUser(@PathVariable("userId1") UUID requesterId, @PathVariable("userId2") UUID requesteeId) {
		
		User user = userService.followUser(requesterId, requesteeId);
		
		return user;
	}
	
	@GetMapping("/users/search")
	public List<User> searchUser(@RequestParam("query") String query) {
		
		List<User> users = userService.searchUser(query);
		
		return users;
	}
	
	
//	@DeleteMapping("/users/{userId}")
//	public String deleteUser(@PathVariable Integer userId) {
//		
//		Optional<User> user = userRepository.findById(userId);
//		
//		if (user.isEmpty()) {
//			throw new RuntimeException("User with id " + userId + " not found");
//		}
//		
//		userRepository.delete(user.get());
//		
//		return "User deleted successfully with id " + userId;
//	}
}
