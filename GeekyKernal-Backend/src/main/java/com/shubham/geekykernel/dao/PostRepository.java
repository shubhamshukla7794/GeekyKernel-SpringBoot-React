package com.shubham.geekykernel.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shubham.geekykernel.entity.Post;

public interface PostRepository extends JpaRepository<Post, UUID>{

	@Query("SELECT p FROM Post p WHERE p.user.id=:userId")
	List<Post> findPostByUserId(UUID userId);
}
