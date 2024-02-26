package com.shubham.geekykernel.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.entity.Comment;
import com.shubham.geekykernel.entity.Post;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.service.CommentService;
import com.shubham.geekykernel.service.PostService;
import com.shubham.geekykernel.service.UserService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	private CommentService commentService;
	private UserService userService;
	private PostService postService;
	
	public CommentController(CommentService commentService, UserService userService, PostService postService) {
		this.commentService = commentService;
		this.userService = userService;
		this.postService = postService;
	}
	
	@PostMapping("/post/{postId}")
	public Comment createComment(@RequestHeader("Authorization") String jwt, 
			@RequestBody Comment comment, @PathVariable UUID postId) {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment createdComment = commentService.createComment(comment, postId, user.getId()); 
		
		return createdComment;
	}
	
	@PutMapping("/like/{commentId}")
	public Comment likeComment(@RequestHeader("Authorization") String jwt, @PathVariable UUID commentId) {
		
		User user = userService.findUserByJwt(jwt);
		Comment likedComment = commentService.likeComment(commentId, user.getId());		
		
		return likedComment;
	}
	
}
