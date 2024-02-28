package com.shubham.geekykernel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubham.geekykernel.entity.Message;
import com.shubham.geekykernel.entity.User;
import com.shubham.geekykernel.service.MessageService;
import com.shubham.geekykernel.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/message")
public class MessageController {

	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageService messageService, UserService userService) {
		this.messageService = messageService;
		this.userService = userService;
	}

	@PostMapping("/chat/{chatId}")
	public Message createMessage(@RequestHeader("Authorization") String jwt,
			@RequestBody Message request, @PathVariable UUID chatId) {
		
		User user = userService.findUserByJwt(jwt);
		Message message = messageService.createMessage(user, chatId, request);
		
		return message;
	}
	
	@GetMapping("/chat/{chatId}")
	public List<Message> findChatsMessages(@PathVariable UUID chatId) {
	    
//		User user = userService.findUserByJwt(jwt);
		List<Message> messages = messageService.findChatsMessages(chatId);
		
		return messages;
	}

	
}
