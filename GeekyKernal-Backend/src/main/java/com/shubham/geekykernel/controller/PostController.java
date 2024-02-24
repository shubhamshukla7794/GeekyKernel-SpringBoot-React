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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.entity.Post;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.response.ApiResponse;
import com.shubham.geekykernel.service.PostService;
import com.shubham.geekykernel.service.UserService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	private PostService postService;
	private UserService userService;

	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping("")
	public ResponseEntity<List<Post>> findAllPost() {

		List<Post> posts = postService.findAllPost();

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post) throws Exception {

		User user = userService.findUserByJwt(jwt);
		Post createdPost = postService.createNewPost(post, user.getId());

		return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization") String jwt, @PathVariable UUID postId) {
		
		User user = userService.findUserByJwt(jwt);
		String message = postService.deletePost(postId, user.getId());
		ApiResponse response = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> findPostById(@PathVariable UUID postId) {

		Post post = postService.findPostById(postId);

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable UUID userId) {

		List<Post> posts = postService.findPostByUserId(userId);

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@PutMapping("/save/{postId}")
	public ResponseEntity<Post> savePost(@RequestHeader("Authorization") String jwt, @PathVariable UUID postId) {

		User user = userService.findUserByJwt(jwt);
		Post post = postService.savedPost(postId, user.getId());

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@PutMapping("/like/{postId}")
	public ResponseEntity<Post> likePost(@RequestHeader("Authorization") String jwt, @PathVariable UUID postId) {

		User user = userService.findUserByJwt(jwt);
		Post post = postService.likePost(postId, user.getId());

		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}
}
