package com.shubham.geekykernel.service;

import java.util.UUID;

import com.shubham.geekykernel.entity.Comment;

public interface CommentService {

	public Comment createComment(Comment comment, UUID postId, UUID userId);
	
	public Comment findCommentById(UUID commentId);
	
	public Comment likeComment(UUID commentId, UUID userId);
	
}
