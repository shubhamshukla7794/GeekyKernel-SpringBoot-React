package com.shubham.geekykernel.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.geekykernel.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID>{

}
