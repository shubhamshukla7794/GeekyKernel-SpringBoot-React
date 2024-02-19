package com.shubham.geekykernel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.dao.PostRepository;
import com.shubham.geekykernel.dao.UserRepository;
import com.shubham.geekykernel.entity.Post;
import com.shubham.geekykernel.entity.User;

@Service
public class PostServiceImpl implements PostService{
	
	private PostRepository postRepository;
	private UserService userService;
	private UserRepository userRepository;

	public PostServiceImpl(PostRepository postRepository, UserService userService, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@Override
	public Post createNewPost(Post post, UUID userId) throws Exception {

		User user = userService.findUserById(userId);
		
		Post newPost = new Post();
		
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setUser(user);
		newPost.setCreatedAt(LocalDateTime.now());
		
		return postRepository.save(newPost);
	}

	@Override
	public String deletePost(UUID postId, UUID userId) {

		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (post.getUser().getId() != user.getId()) {
			throw new RuntimeException("You cannot delete another user's post");
		}
		
		postRepository.delete(post);
		
		return "Post deleted successfully!!!";
	}

	@Override
	public List<Post> findPostByUserId(UUID userId) {
		
		return postRepository.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(UUID postId) {
		
		Optional<Post> post = postRepository.findById(postId);
		
		if (post.isEmpty()) {
			throw new RuntimeException("Post with post id - " + postId + " not found.");
		}
		
		return post.get();
	}

	@Override
	public List<Post> findAllPost() {
		
		return postRepository.findAll();
	}

	@Override
	public Post savedPost(UUID postId, UUID userId) {

		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		} else {
			user.getSavedPost().add(post);
		}
		
		userRepository.save(user);
		
		return post;
	}

	@Override
	public Post likePost(UUID postId, UUID userId) {
		
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		} else {
			post.getLiked().add(user);
		}
		
		return postRepository.save(post);
	}

}
