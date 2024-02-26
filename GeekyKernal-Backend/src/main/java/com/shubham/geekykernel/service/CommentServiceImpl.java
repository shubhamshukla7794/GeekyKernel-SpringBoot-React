package com.shubham.geekykernel.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.dao.CommentRepository;
import com.shubham.geekykernel.dao.PostRepository;
import com.shubham.geekykernel.entity.Comment;
import com.shubham.geekykernel.entity.Post;
import com.shubham.geekykernel.entity.User;

@Service
public class CommentServiceImpl implements CommentService {
	
	private UserService userService;
	private PostService postService;
	private PostRepository postRepository;
	private CommentRepository commentRepository;

	public CommentServiceImpl(UserService userService, PostService postService, PostRepository postRepository,
			CommentRepository commentRepository) {
		this.userService = userService;
		this.postService = postService;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public Comment createComment(Comment comment, UUID postId, UUID userId) {

		User user = userService.findUserById(userId);
		Post post = postService.findPostById(postId);
		
		Comment newComment = new Comment();
		
		newComment.setUser(user);
		newComment.setContent(comment.getContent());
		newComment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(newComment);
		
		post.getComments().add(savedComment);
		
		postRepository.save(post);
		
		return savedComment;
	}

	@Override
	public Comment findCommentById(UUID commentId) {

		Optional<Comment> comment = commentRepository.findById(commentId);
		
		if (comment.isEmpty()) {
			throw new RuntimeException("Comment with id - " + commentId + "not found!!!");
		}
		
		return comment.get();
	}

	@Override
	public Comment likeComment(UUID commentId, UUID userId) {
		Comment comment = findCommentById(commentId);
		User user = userService.findUserById(userId);
		
		if (comment.getLiked().contains(user)) {
			comment.getLiked().remove(user);
		} else {
			comment.getLiked().add(user);
		}
		
		return commentRepository.save(comment);
	}

}
