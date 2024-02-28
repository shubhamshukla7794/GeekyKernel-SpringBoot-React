package com.shubham.geekykernel.service;

import java.util.List;
import java.util.UUID;

import com.shubham.geekykernel.entity.Message;
import com.shubham.geekykernel.entity.User;

public interface MessageService {
	
	public Message createMessage(User user, UUID chatId, Message request);
	
	public List<Message> findChatsMessages (UUID chatId);
	
}
