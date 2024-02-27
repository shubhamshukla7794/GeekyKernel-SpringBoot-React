package com.shubham.geekykernel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.entity.Chat;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.request.CreateChatRequest;
import com.shubham.geekykernel.service.ChatService;
import com.shubham.geekykernel.service.UserService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	
	private ChatService chatService;
	private UserService userService;
	
	public ChatController(ChatService chatService, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}

	@PostMapping("")
	public Chat createChat(@RequestHeader("Authorization") String jwt, 
						   @RequestBody CreateChatRequest request) {
		
		User initiatorUser = userService.findUserByJwt(jwt);
		User recipientUser = userService.findUserById(request.getUserId()); 
		Chat chat = chatService.createChat(initiatorUser, recipientUser);
		
		return  chat;
		
	}
	
	@GetMapping("")
	public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) {
		
		User user = userService.findUserByJwt(jwt);
		List<Chat> chats = chatService.findUsersChats(user.getId());
		
		return  chats;
		
	}
	
}
