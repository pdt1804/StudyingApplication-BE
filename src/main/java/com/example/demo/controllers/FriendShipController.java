package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.FriendShipService;

@RestController
@RequestMapping("/api/v1/friendship")
public class FriendShipController {

	@Autowired
	private FriendShipService friendShipService;
	
	@GetMapping("/getAllFriendList")
	public List<User> getAllFriendListofUser(@RequestParam("myUserName") String myUserName)
	{
		return friendShipService.getAllFriendofUser(myUserName);
	}
	
	@GetMapping("/getAllInvitationFriendList")
	public List<User> getAllInvitationFriendListofUser(@RequestParam("myUserName") String myUserName)
	{
		return friendShipService.getAllInvatationFriendofUser(myUserName);
	}
	
	@PostMapping("/refuseInvitation")
	public void refuseInvitation(@RequestParam("sentUserName") String sentUserName, @RequestParam("myUserName") String myUserName)
	{
		friendShipService.refuseToAddFriend(sentUserName, myUserName);
	}
	
	@PostMapping("/acceptInvitation")
	public void acceptInvitation(@RequestParam("sentUserName") String sentUserName, @RequestParam("myUserName") String myUserName)
	{
		friendShipService.acceptToAddFriend(sentUserName, myUserName);
	}
	
	@PostMapping("/addFriend")
	public void addFriend(@RequestParam("sentUserName") String sentUserName, @RequestParam("receivedUserName") String receivedUserName)
	{
		friendShipService.AddFriend(sentUserName, receivedUserName);
	}

	@PostMapping("/undoInvitationFriend")
	public void undoInvitationFriend(@RequestParam("sentUserName") String sentUserName, @RequestParam("receivedUserName") String receivedUserName)
	{
		friendShipService.undoInvitationFriend(sentUserName, receivedUserName);
	}
	
	@DeleteMapping("/deleteFriend")
	public void deleteFriend(@RequestParam("sentUserName") String sentUserName, @RequestParam("receivedUserName") String receivedUserName)
	{
		friendShipService.deleteFriendShip(sentUserName, receivedUserName);
	}
	
	@PostMapping("/checkNewMessage")
	public boolean checkNewMessage(@RequestParam("fromUserName") String fromUserName, @RequestParam("myUserName") String myUserName)
	{
		return friendShipService.checkNewMessage(fromUserName, myUserName);
	}
	
	@GetMapping("/findAllFriendByInputName")
	public List<User> findAllFriendByInputName(@RequestParam("input") String input)
	{
		return friendShipService.findFriend(input);
	}
	
	@GetMapping("/findAllFriendByUserName")
	public List<User> findAllFriendByUserName(@RequestParam("userName") String userName)
	{
		return friendShipService.findFriendbyUserName(userName);
	}
	
}
