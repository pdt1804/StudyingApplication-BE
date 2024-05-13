package com.example.demo.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.User;

public interface MessageGroupManagement {

	public long sendMessage(MessageGroup mess, int groupID, String userName);
	
	public List<MessageGroup> loadMessage(String myUserName, int groupID);
	
	public User getSentUserInGroup(long id);
}
