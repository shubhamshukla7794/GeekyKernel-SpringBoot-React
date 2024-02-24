package com.shubham.geekykernel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.dao.UserRepository;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserRepository userRepository;	
	private UserService userService;
	
	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@GetMapping("")
	public List<User> getUsers() {
		
		List<User> users = userRepository.findAll();
		
		return users;
	}
	
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") UUID id) {
				
		return userService.findUserById(id);
	}
	
	@PutMapping("")
	public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) {
		
		User reqUser = userService.findUserByJwt(jwt);
		User updatedUser = userService.updateUser(user, reqUser.getId());
		
		return updatedUser;
	}
	
	@PutMapping("/follow/{userId}")
	public User followUser(@RequestHeader("Authorization") String jwt, @PathVariable("userId") UUID requesteeId) {

		User reqUser = userService.findUserByJwt(jwt);
		
		User user = userService.followUser(reqUser.getId(), requesteeId);
		user.setPassword(null);
		return user;
	}
	
	@GetMapping("/search")
	public List<User> searchUser(@RequestParam("query") String query) {
		
		List<User> users = userService.searchUser(query);
		
		return users;
	}
	
	@GetMapping("/profile")
	public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
		
		User user = userService.findUserByJwt(jwt);
		user.setPassword(null);
		return user;
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
