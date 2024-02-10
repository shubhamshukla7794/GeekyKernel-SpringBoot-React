package com.shubham.geekykernel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.dao.UserRepository;
import com.shubham.geekykernel.entity.User;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerUser(User user) {
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setId(user.getId());
		
		User savedUser = userRepository.save(newUser);
		
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty()) {
			throw new RuntimeException("User with id " + userId + " not found");
		}
				
		return user.get();
	}

	@Override
	public User findUserByEmail(String email) {

		User user = userRepository.findByEmail(email);
			
		return user;
	}

	@Override
	public User followUser(Integer userId1, Integer userId2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> searchUser(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
