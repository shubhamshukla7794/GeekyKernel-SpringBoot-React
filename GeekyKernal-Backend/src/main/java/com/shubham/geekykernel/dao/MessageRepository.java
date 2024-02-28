package com.shubham.geekykernel.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.geekykernel.entity.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {
	
	public List<Message> findByChatId(UUID chatId);
}
