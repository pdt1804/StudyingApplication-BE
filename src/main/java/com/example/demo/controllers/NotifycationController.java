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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.NotifycationDTO;
import com.example.demo.entities.Notifycation;
import com.example.demo.services.JwtService;
import com.example.demo.services.NotifycationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/notifycation")
public class NotifycationController {

	@Autowired
	private NotifycationService notifycationService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@PostMapping("/create")
	public int createNotifycation(@RequestParam("groupID") int groupID, HttpServletRequest request, @RequestBody Notifycation notifycation)
	{
		return notifycationService.createNotifycation(groupID, extractTokenToGetUsername(request), notifycation);
	}
	
	@PostMapping("/insertImage")
	public void insertImage(@RequestParam("notificationID") int notificationID, @RequestParam("image") MultipartFile image)
	{
		notifycationService.insertImage(notificationID, image);
	}
	
	@GetMapping("/getAllNotifycationbyGroupID")
	public List<Notifycation> getAllNotifycationbyGroupID(@RequestParam("groupID") int groupID)
	{
		return notifycationService.getAllNotifycationByGroupID(groupID);
	}
	
	@GetMapping("/getAllNotifycationbyUserName")
	public List<Notifycation> getAllNotifycationbyUserName(HttpServletRequest request)
	{
		return notifycationService.getAllNotifycationByUserName(extractTokenToGetUsername(request));
	}
	
	@PostMapping("/checkNewNotifycation")
	public boolean checkNewNotifycation(HttpServletRequest request, @RequestParam("notifycationID") int notifycationID)
	{
		return notifycationService.checkNewNotifycation(extractTokenToGetUsername(request), notifycationID);
	}
	
	@GetMapping("/loadNotifycation")
	public NotifycationDTO loadNotifycation(HttpServletRequest request, @RequestParam("notifycationID") int notifycationID)
	{
		return notifycationService.loadNotifycation(extractTokenToGetUsername(request), notifycationID);
	}
	
	@DeleteMapping("/deleteNotifycationForAllMembers")
	public String deleteNotifycationForAllMembers(HttpServletRequest request, @RequestParam("notifycationID") int notifycationID, @RequestParam("groupID") int groupID)
	{
		return notifycationService.deleteNotifycationByLeaderGroupForAll(extractTokenToGetUsername(request), notifycationID, groupID);
	}
	
	@DeleteMapping("/deleteNotifycationForMyAccount")
	public void deleteNotifycationForMyAccount(HttpServletRequest request, @RequestParam("notifycationID") int notifycationID, @RequestParam("groupID") int groupID)
	{
		notifycationService.deleteNotifycationForMyAccount(extractTokenToGetUsername(request), notifycationID, groupID);
	}
}
