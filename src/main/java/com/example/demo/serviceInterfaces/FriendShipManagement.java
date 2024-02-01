package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entities.FriendShip;
import com.example.demo.entities.FriendShipStatus;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.entities.User;

public interface FriendShipManagement {

	public List<User> getAllSentInvitationList(String userName);
	
	public void AddFriend(String sentUserName, String receivedUserName);
	
	public void undoInvitationFriend(String sentUserName, String receivedUserName);
	
	public void deleteFriendShip(String sentUserName, String receivedUserName);
	
	public List<User> getAllFriendofUser(String userName);
	
	public List<User> getAllInvatationFriendofUser(String userName);
	
	public void refuseToAddFriend(String sentUserName, String myUserName);
	
	public void acceptToAddFriend(String sentUserName, String myUserName);
	
	public boolean checkNewMessage(String fromUserName, String myUserName);
	
	public List<User> findFriend(String input, String userName);
	
	public List<User> findFriendbyUserName(String userName);
}
