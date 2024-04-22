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
import com.example.demo.serviceInterfaces.FriendShipManagement;

@Service
public class FriendShipService implements FriendShipManagement{

	@Autowired
	private FriendShipRepository friendShipRepository;
	
	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<User> getAllSentInvitationList(String userName)
	{
		List<User> users = new ArrayList<User>();

		for (var f : friendShipRepository.findAll())
		{
			if (f.getUser().getUserName().equals(userName))
			{
				if (f.getStatus() == FriendShipStatus.Đã_gửi)
				{
					users.add(f.getFriend());
				}
			}
		}
		
		return users;
	}
	
	@Override
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

	@Override
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

	@Override
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

	@Override
	public List<User> getAllFriendofUser(String userName)
	{
		var listFriendShip = friendShipRepository.findAll().stream().filter(p -> 
		{
			return (p.getFriend().getUserName().equals(userName) && p.getStatus() == FriendShipStatus.Đã_xác_nhận) 
					|| (p.getUser().getUserName().equals(userName) && p.getStatus() == FriendShipStatus.Đã_xác_nhận);
			
		}).sorted((f1,f2) -> f2.getLastTimeEdited().compareTo(f1.getLastTimeEdited())).collect(Collectors.toList());
		
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

	@Override
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

	@Override
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

	@Override
	public void acceptToAddFriend(String sentUserName, String myUserName)
	{
		System.out.println(sentUserName);
		System.out.println(myUserName);

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

	@Override
	public boolean checkNewMessage(String fromUserName, String myUserName)
	{
		return messageUserRepository.findAll().stream()
				.anyMatch(p -> p.getReceivedUser().getUserName().equals(myUserName) 
						&& p.getSentUser().getUserName().equals(fromUserName) 
						&& p.getStatus() == MessageUserStatus.sent);
	}

	@Override
	public List<User> findFriend(String input, String userName)
	{
		List<User> users = new ArrayList<User>();
		
		for (var p : userRepository.findAll())
		{
			if (p.getInformation().getFulName().toLowerCase().contains(input.toLowerCase()))
			{
				users.add(p);
			}
		}

		for (var f : friendShipRepository.findAll())
		{
			if (f.getUser().getUserName().equals(userName))
			{
				if (f.getStatus() == FriendShipStatus.Đã_gửi || f.getStatus() == FriendShipStatus.Đã_xác_nhận)
				{
					users.remove(f.getFriend());
				}
			}
			else if (f.getFriend().getUserName().equals(userName))
			{
				if (f.getStatus() == FriendShipStatus.Đã_gửi || f.getStatus() == FriendShipStatus.Đã_xác_nhận)
				{
					users.remove(f.getUser());
				}
			}
			else
			{
				
			}
		}
		users.remove(userRepository.getById(userName));
		return users;
	}

	@Override
	public List<User> findFriendbyUserName(String userName)
	{
		return userRepository.findAll().stream().filter(p -> p.getUserName().equals(userName)).toList();
	}
}
