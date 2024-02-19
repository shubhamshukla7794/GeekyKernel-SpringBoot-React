package com.shubham.geekykernel.service;

import java.util.List;
import java.util.UUID;

import com.shubham.geekykernel.entity.User;

public interface UserService {

	public User registerUser(User user);
	
	public User findUserById(UUID userId);
	
	public User findUserByEmail(String email);
	
	public User followUser(UUID userId1, UUID userId2);
	
	public User updateUser(User user, UUID userId);
	
	public List<User> searchUser(String query);
	
}
