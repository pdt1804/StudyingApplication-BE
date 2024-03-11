package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.FriendShipService;
import com.example.demo.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/friendship")
public class FriendShipController {

	@Autowired
	private FriendShipService friendShipService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/getAllFriendList")
	public List<User> getAllFriendListofUser(HttpServletRequest request)
	{
		return friendShipService.getAllFriendofUser(extractTokenToGetUsername(request));
	}
	
	@GetMapping("/getAllSentInvitationList")
	public List<User> getAllSentInvitationList(HttpServletRequest request)
	{
		return friendShipService.getAllSentInvitationList(extractTokenToGetUsername(request));
	}
	
	@GetMapping("/getAllInvitationFriendList")
	public List<User> getAllInvitationFriendListofUser(HttpServletRequest request)
	{
		return friendShipService.getAllInvatationFriendofUser(extractTokenToGetUsername(request));
	}
	
	@PostMapping("/refuseInvitation")
	public void refuseInvitation(@RequestParam("sentUserName") String sentUserName, HttpServletRequest request)
	{
		friendShipService.refuseToAddFriend(sentUserName, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/acceptInvitation")
	public void acceptInvitation(@RequestParam("sentUserName") String sentUserName, HttpServletRequest request)
	{
		friendShipService.acceptToAddFriend(sentUserName, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/addFriend")
	public void addFriend(HttpServletRequest request, @RequestParam("receivedUserName") String receivedUserName)
	{	
		friendShipService.AddFriend(extractTokenToGetUsername(request), receivedUserName);
	}

	@PostMapping("/undoInvitationFriend")
	public void undoInvitationFriend(HttpServletRequest request, @RequestParam("receivedUserName") String receivedUserName)
	{
		friendShipService.undoInvitationFriend(extractTokenToGetUsername(request), receivedUserName);
	}
	
	@DeleteMapping("/deleteFriend/{userName}")
	public void deleteFriend(@PathVariable("userName") String userName, HttpServletRequest request)
	{	
		System.out.println(userName);
		System.out.println(extractTokenToGetUsername(request));
		
		friendShipService.deleteFriendShip(userName, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/checkNewMessage")
	public boolean checkNewMessage(@RequestParam("fromUserName") String fromUserName, HttpServletRequest request)
	{
		return friendShipService.checkNewMessage(fromUserName, extractTokenToGetUsername(request));
	}
	
	@GetMapping("/findAllFriendByInputName")
	public List<User> findAllFriendByInputName(@RequestParam("input") String input, HttpServletRequest request)
	{
		return friendShipService.findFriend(input, extractTokenToGetUsername(request));
	}
	
	@GetMapping("/findAllFriendByUserName")
	public List<User> findAllFriendByUserName(HttpServletRequest request)
	{
		return friendShipService.findFriendbyUserName(extractTokenToGetUsername(request));
	}
	
}
