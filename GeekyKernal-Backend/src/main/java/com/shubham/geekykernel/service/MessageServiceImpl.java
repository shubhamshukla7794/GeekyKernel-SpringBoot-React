package com.shubham.geekykernel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.dao.ChatRepository;
import com.shubham.geekykernel.dao.MessageRepository;
import com.shubham.geekykernel.entity.Chat;
import com.shubham.geekykernel.entity.Message;
import com.shubham.geekykernel.entity.User;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;
	private ChatService chatService;	
	private ChatRepository chatRepository;

	
	public MessageServiceImpl(MessageRepository messageRepository, ChatService chatService,
			ChatRepository chatRepository) {
		this.messageRepository = messageRepository;
		this.chatService = chatService;
		this.chatRepository = chatRepository;
	}

	@Override
	public Message createMessage(User user, UUID chatId, Message request) {

		Chat chat = chatService.findChatById(chatId);
		Message message = new Message();
		
		message.setChat(chat);
		message.setContent(request.getContent());
		message.setImage(request.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());
		
		Message savedMessage = messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(UUID chatId) {
		
		Chat chat = chatService.findChatById(chatId);
		
		return messageRepository.findByChatId(chat.getId());
	}

}
