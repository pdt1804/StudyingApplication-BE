package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.MessageUser;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.repositories.FriendShipRepository;
import com.example.demo.repositories.MessageUserRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class MessageUserService {

	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private FriendShipRepository friendShipRepository;
	
	public long sendMessage(MessageUser mess, String fromUserName, String toUserName)
	{
		try 
		{
			var fromUser = userRepository.getById(fromUserName);
			var toUser = userRepository.getById(toUserName);
			
			mess.setStatus(MessageUserStatus.sent);
			mess.setSentUser(fromUser);
			mess.setReceivedUser(toUser);
			mess.setDateSent(new Date());
						
			var friendShip = friendShipRepository.findAll().stream()
					.filter(p -> (p.getUser().getUserName().equals(fromUserName) && p.getFriend().getUserName().equals(toUserName)) 
							|| (p.getUser().getUserName().equals(toUserName) && p.getFriend().getUserName().equals(fromUserName)))
					.findFirst().orElse(null);
			friendShip.setLastTimeEdited(new Date());
			
			if (friendShip != null)
			{
				return messageUserRepository.save(mess).getID();
			}
			else
			{
				return -1;
			}
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
				mess.setStatus(MessageUserStatus.seen);
				listMessageUser.add(mess);
				messageUserRepository.save(mess);
			}
			else
			{
				continue;
			}
		}
		
		return listMessageUser.stream().sorted((m1,m2) -> m1.getDateSent().compareTo(m2.getDateSent())).collect(Collectors.toList());
	}
	
}
