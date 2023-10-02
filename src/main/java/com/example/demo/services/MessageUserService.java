package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.MessageUser;
import com.example.demo.repositories.MessageUserRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class MessageUserService {

	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public long sendMessage(MessageUser mess, String fromUserName, String toUserName)
	{
		try 
		{
			var fromUser = userRepository.getById(fromUserName);
			var toUser = userRepository.getById(toUserName);
			
			mess.setSentUser(fromUser);
			mess.setReceivedUser(toUser);
			mess.setDateSent(new Date());
			
			return messageUserRepository.save(mess).getID();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<MessageUser> loadMessageInUser(String fromUserName, String toUserName)
	{
		List<MessageUser> listMessageUser = new ArrayList<>();
		
		for (var mess : messageUserRepository.findAll())
		{
			if (mess.getSentUser().getUserName().equals(fromUserName) && mess.getReceivedUser().getUserName().equals(toUserName))
			{
				listMessageUser.add(mess);
			}
			else if (mess.getSentUser().getUserName().equals(toUserName) && mess.getReceivedUser().getUserName().equals(fromUserName))
			{
				listMessageUser.add(mess);
			}
			else
			{
				continue;
			}
		}
		
		return listMessageUser.stream().sorted((m1,m2) -> m2.getDateSent().compareTo(m1.getDateSent())).collect(Collectors.toList());
	}
}
