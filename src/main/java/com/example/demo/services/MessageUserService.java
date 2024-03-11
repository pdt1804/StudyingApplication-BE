package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entities.MessageUser;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.entities.User;
import com.example.demo.repositories.FriendShipRepository;
import com.example.demo.repositories.MessageUserRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.MessageUserManagement;

@Service
public class MessageUserService implements MessageUserManagement{

	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private FriendShipRepository friendShipRepository;
	
	@Override
	public User getSentUser(long id)
	{
		return messageUserRepository.getById(id).getSentUser();
	}
	
	@Override
	public long sendMessage(MessageUser mess, String fromUserName, String toUserName)
	{
		try 
		{
			if (toUserName.equals("Chatbot") || fromUserName.equals("Chatbot"))
			{
				var fromUser = userRepository.getById(fromUserName);
				var toUser = userRepository.getById(toUserName);
				
				mess.setStatus(MessageUserStatus.seen);
				mess.setSentUser(fromUser);
				mess.setReceivedUser(toUser);
				mess.setDateSent(new Date());
				
				return messageUserRepository.save(mess).getID();
			}
			else
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
				
				if (friendShip != null)
				{
					friendShip.setLastTimeEdited(new Date());
					friendShipRepository.save(friendShip);
					
					var message = messageUserRepository.save(mess);
					
					messagingTemplate.convertAndSend("/private/queue/" + friendShip.getFriendShip_ID(), message);
					return message.getID();
				}
				else
				{
					return -1;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public long saveChatBotMessage(MessageUser mess, String toUserName)
	{
		try 
		{
			var fromUser = userRepository.getById("Chatbot");
			var toUser = userRepository.getById(toUserName);
			
			mess.setStatus(MessageUserStatus.seen);
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
	
	@Override
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
	
	@Override
	public int getFriendID(String fromUserName, String toUserName)
	{
		return friendShipRepository.findAll().stream().filter(p -> (p.getUser().getUserName().equals(fromUserName) && p.getFriend().getUserName().equals(toUserName)) || (p.getUser().getUserName().equals(toUserName) && p.getFriend().getUserName().equals(fromUserName))).findFirst().get().getFriendShip_ID();
	}
	
}
