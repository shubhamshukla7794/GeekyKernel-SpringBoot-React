package com.shubham.geekykernel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.geekykernel.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email); 
}
