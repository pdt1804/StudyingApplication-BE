package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.NotifycationDTO;
import com.example.demo.entities.Notifycation;
import com.example.demo.services.NotifycationService;

@RestController
@RequestMapping("/api/v1/notifycation")
public class NotifycationController {

	@Autowired
	private NotifycationService notifycationService;
	
	@PostMapping("/create")
	public void createNotifycation(@RequestParam("groupID") int groupID, @RequestParam("userName") String userName, @RequestBody Notifycation notifycation)
	{
		notifycationService.createNotifycation(groupID, userName, notifycation);
	}
	
	@GetMapping("/getAllNotifycationbyGroupID")
	public List<Notifycation> getAllNotifycationbyGroupID(@RequestParam("groupID") int groupID)
	{
		return notifycationService.getAllNotifycationByGroupID(groupID);
	}
	
	@GetMapping("/getAllNotifycationbyUserName")
	public List<Notifycation> getAllNotifycationbyUserName(@RequestParam("userName") String userName)
	{
		return notifycationService.getAllNotifycationByUserName(userName);
	}
	
	@PostMapping("/checkNewNotifycation")
	public boolean checkNewNotifycation(@RequestParam("myUserName") String myUserName, @RequestParam("notifycationID") int notifycationID)
	{
		return notifycationService.checkNewNotifycation(myUserName, notifycationID);
	}
	
	@GetMapping("/loadNotifycation")
	public NotifycationDTO loadNotifycation(@RequestParam("myUserName") String myUserName, @RequestParam("notifycationID") int notifycationID)
	{
		return notifycationService.loadNotifycation(myUserName, notifycationID);
	}
	
	@DeleteMapping("/deleteNotifycationForAllMembers")
	public void deleteNotifycationForAllMembers(@RequestParam("userName") String userName, @RequestParam("notifycationID") int notifycationID, @RequestParam("groupID") int groupID)
	{
		notifycationService.deleteNotifycationByLeaderGroupForAll(userName, notifycationID, groupID);
	}
	
	@DeleteMapping("/deleteNotifycationForMyAccount")
	public void deleteNotifycationForMyAccount(@RequestParam("userName") String userName, @RequestParam("notifycationID") int notifycationID, @RequestParam("groupID") int groupID)
	{
		notifycationService.deleteNotifycationForMyAccount(userName, notifycationID, groupID);
	}
}
