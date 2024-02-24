package com.shubham.geekykernel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.config.JwtProvider;
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
//		newUser.setId(user.getId());
		newUser.setGender(user.getGender());
		
		User savedUser = userRepository.save(newUser);
		
		return savedUser;
	}

	@Override
	public User findUserById(UUID userId) {
		
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
	public User followUser(UUID requesterId, UUID requesteeId) {
		
		User requesterUser = findUserById(requesterId);
		User requesteeUser = findUserById(requesteeId);
		
		requesteeUser.getFollowers().add(requesterUser.getId());
		requesterUser.getFollowing().add(requesteeUser.getId());
		
		userRepository.save(requesterUser);
		userRepository.save(requesteeUser);
		
		return requesterUser;
	}

	@Override
	public User updateUser(User user, UUID userId) {
		
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
		if (user.getGender() != null) {
			oldUser.setGender(user.getGender());
		}
		
		User updatedUser = userRepository.save(oldUser);
		
		return updatedUser;
	}

	@Override
	public List<User> searchUser(String query) {
		
		return userRepository.searchUser(query);
	}

	@Override
	public User findUserByJwt(String jwt) {

		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}

}
