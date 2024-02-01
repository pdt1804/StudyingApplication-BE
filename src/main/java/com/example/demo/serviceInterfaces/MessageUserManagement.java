package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entities.MessageUser;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.entities.User;

public interface MessageUserManagement {

	public User getSentUser(long id);
	
	public long sendMessage(MessageUser mess, String fromUserName, String toUserName);
	
	public List<MessageUser> loadMessageInUser(String fromUserName, String toUserName);
	
	public long saveChatBotMessage(MessageUser mess, String toUserName);
}
