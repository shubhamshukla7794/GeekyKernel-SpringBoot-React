package com.shubham.geekykernel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.entity.Post;
import com.shubham.geekykernel.response.ApiResponse;
import com.shubham.geekykernel.service.PostService;

@RestController
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/post")
	public ResponseEntity<List<Post>> findAllPost() {

		List<Post> posts = postService.findAllPost();

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	@PostMapping("/post/user/{userId}")
	public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable UUID userId) throws Exception {

		Post createdPost = postService.createNewPost(post, userId);

		return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/post/{postId}/user/{userId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable UUID postId, @PathVariable UUID userId) {

		String message = postService.deletePost(postId, userId);
		ApiResponse response = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<Post> findPostById(@PathVariable UUID postId) {

		Post post = postService.findPostById(postId);

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@GetMapping("/post/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable UUID userId) {

		List<Post> posts = postService.findPostByUserId(userId);

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@PutMapping("/post/save/{postId}/user/{userId}")
	public ResponseEntity<Post> savePost(@PathVariable UUID postId, @PathVariable UUID userId) {

		Post post = postService.savedPost(postId, userId);

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@PutMapping("/post/like/{postId}/user/{userId}")
	public ResponseEntity<Post> likePost(@PathVariable UUID postId, @PathVariable UUID userId) {

		Post post = postService.likePost(postId, userId);

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}
}
