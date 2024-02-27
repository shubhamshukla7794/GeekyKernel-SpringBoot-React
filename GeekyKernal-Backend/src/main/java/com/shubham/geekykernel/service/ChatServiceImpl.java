package com.shubham.geekykernel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shubham.geekykernel.dao.ChatRepository;
import com.shubham.geekykernel.entity.Chat;
import com.shubham.geekykernel.entity.User;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatRepository chatRepository;
	
	public ChatServiceImpl(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	@Override
	public Chat createChat(User initiatorUser, User recipientUser) {
		
		Chat isExist = chatRepository.findChatByUsersId(initiatorUser, recipientUser);
		
		if (isExist != null) {
			return isExist;
		}
		
		Chat chat = new Chat();
		chat.getUsers().add(initiatorUser);
		chat.getUsers().add(recipientUser);
		chat.setTimestamp(LocalDateTime.now());
		
		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(UUID chatId) {

		Optional<Chat> chat = chatRepository.findById(chatId);
		
		if (chat.isEmpty()) {
			throw new RuntimeException("Chat with id - " + chatId + " not found!!!");
		}
		
		return chat.get();
	}

	@Override
	public List<Chat> findUsersChats(UUID userId) {
		return chatRepository.findByUsersId(userId);
	}

}
