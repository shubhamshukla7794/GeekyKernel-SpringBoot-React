package com.shubham.geekykernel.service;

import java.util.List;
import java.util.UUID;

import com.shubham.geekykernel.entity.Chat;
import com.shubham.geekykernel.entity.User;

public interface ChatService {
	
	public Chat createChat(User initiatorUser, User recipientUser);
	
	public Chat findChatById(UUID chatId);
	
	public List<Chat> findUsersChats(UUID userId);
	
}
