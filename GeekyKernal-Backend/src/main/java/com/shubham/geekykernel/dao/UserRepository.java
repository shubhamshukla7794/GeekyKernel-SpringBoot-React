package com.shubham.geekykernel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shubham.geekykernel.entity.User;
import java.util.List;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

	public User findByEmail(String email); 
	
	@Query("SELECT u FROM User u WHERE u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);
}
