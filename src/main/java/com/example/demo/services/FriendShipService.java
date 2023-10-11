package com.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.FriendShip;
import com.example.demo.entities.FriendShipStatus;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.entities.User;
import com.example.demo.repositories.FriendShipRepository;
import com.example.demo.repositories.MessageUserRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class FriendShipService {

	@Autowired
	private FriendShipRepository friendShipRepository;
	
	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	public void AddFriend(String sentUserName, String receivedUserName)
	{
		try
		{
			friendShipRepository.save(new FriendShip().builder().sentTime(new Date())
																.lastTimeEdited(new Date())
															    .status(FriendShipStatus.Đã_gửi)
													   		    .user(userService.GetUserByUsername(sentUserName))
															    .friend(userService.GetUserByUsername(receivedUserName)).build());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void undoInvitationFriend(String sentUserName, String receivedUserName)
	{
		FriendShip friendShip = friendShipRepository.findAll()
										       	    .stream()
													.filter(p -> (p.getUser().getUserName().equals(sentUserName) && p.getFriend().getUserName().equals(receivedUserName)))
													.findFirst()
													.orElse(null);
		if (friendShip != null)
		{
			friendShipRepository.delete(friendShip);
		}
	}
	
	public void deleteFriendShip(String sentUserName, String receivedUserName)
	{
		FriendShip friendShip = friendShipRepository.findAll()
										       	    .stream()
													.filter(p -> 
																(p.getUser().getUserName().equals(sentUserName) && p.getFriend().getUserName().equals(receivedUserName)) 
																					|| 
																(p.getUser().getUserName().equals(receivedUserName) && p.getFriend().getUserName().equals(sentUserName)))
													.findFirst()
													.orElse(null);
		if (friendShip != null)
		{
			friendShipRepository.delete(friendShip);
		}
	}
	
	public List<User> getAllFriendofUser(String userName)
	{
		var listFriendShip = friendShipRepository.findAll().stream().filter(p -> 
		{
			return (p.getFriend().getUserName().equals(userName) && p.getStatus() == FriendShipStatus.Đã_xác_nhận) 
					|| (p.getUser().getUserName().equals(userName) && p.getStatus() == FriendShipStatus.Đã_xác_nhận);
			
		}).sorted((f1,f2) -> f1.getLastTimeEdited().compareTo(f2.getLastTimeEdited())).collect(Collectors.toList());
		
		List<User> listUser = new ArrayList<>();
		
		for (var p : listFriendShip)
		{
			if (p.getUser().getUserName().equals(userName))
			{
				listUser.add(p.getFriend());
			}
			else
			{
				listUser.add(p.getUser());
			}
		}
		
		return listUser;
	}
	
	public List<User> getAllInvatationFriendofUser(String userName)
	{
		var listFriendShip = friendShipRepository.findAll().stream().filter(p -> 
		{
			return (p.getFriend().getUserName().equals(userName) && p.getStatus() == FriendShipStatus.Đã_gửi);
			
		}).collect(Collectors.toList());
		
		List<User> listUser = new ArrayList<>();
		
		for (var p : listFriendShip)
		{
			listUser.add(p.getUser());
		}

		return listUser;
	}
	
	public void refuseToAddFriend(String sentUserName, String myUserName)
	{
		var friendShip = friendShipRepository.findAll().stream().filter(p -> 
		{
			return (p.getFriend().getUserName().equals(myUserName) 
					&& p.getUser().getUserName().equals(sentUserName) 
					&& p.getStatus() == FriendShipStatus.Đã_gửi );
			
		}).findFirst().orElse(null);
				
		friendShip.setStatus(FriendShipStatus.Đã_từ_chối);
		
		friendShipRepository.save(friendShip);
		
	}
	
	public void acceptToAddFriend(String sentUserName, String myUserName)
	{
		var friendShip = friendShipRepository.findAll().stream().filter(p -> 
		{
			return (p.getFriend().getUserName().equals(myUserName) 
					&& p.getUser().getUserName().equals(sentUserName) 
					&& p.getStatus() == FriendShipStatus.Đã_gửi );
			
		}).findFirst().orElse(null);
				
		friendShip.setLastTimeEdited(new Date());
		friendShip.setStatus(FriendShipStatus.Đã_xác_nhận);
		
		friendShipRepository.save(friendShip);
		
	}
	
	public boolean checkNewMessage(String fromUserName, String myUserName)
	{
		return messageUserRepository.findAll().stream()
				.anyMatch(p -> p.getReceivedUser().getUserName().equals(myUserName) 
						&& p.getSentUser().getUserName().equals(fromUserName) 
						&& p.getStatus() == MessageUserStatus.sent);
	}
	
	public List<User> findFriend(String input)
	{
		return userRepository.findAll().stream().filter(p -> p.getInformation().getFulName().contains(input)).toList();
	}
	
	public List<User> findFriendbyUserName(String userName)
	{
		return userRepository.findAll().stream().filter(p -> p.getUserName().equals(userName)).toList();
	}
}
