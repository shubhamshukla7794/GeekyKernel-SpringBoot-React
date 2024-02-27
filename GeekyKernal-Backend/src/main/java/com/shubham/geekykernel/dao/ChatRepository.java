package com.shubham.geekykernel.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shubham.geekykernel.entity.Chat;
import com.shubham.geekykernel.entity.User;

public interface ChatRepository extends JpaRepository<Chat, UUID>{

	public List<Chat> findByUsersId(UUID userId);
	
	@Query("SELECT c from Chat c WHERE :reqUser MEMBER OF c.users AND :recipUser MEMBER OF c.users")
	public Chat findChatByUsersId(@Param("reqUser") User reqUser, @Param("recipUser") User recipUser);
	
}
