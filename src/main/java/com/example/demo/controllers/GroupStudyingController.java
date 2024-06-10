package com.example.demo.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.GroupStudyingDTO;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.User;
import com.example.demo.services.GroupStudyingService;
import com.example.demo.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/groupStudying")
public class GroupStudyingController {
	
	@Autowired
	private GroupStudyingService groupStudyingService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/getAllGroupofUser")
	public List<GroupStudying> getAllGroupofUser(HttpServletRequest request)
	{
		return groupStudyingService.getAllGroupofUser(extractTokenToGetUsername(request));
	}
	
	@PostMapping("/checkNewMessage")
	public boolean checkNewMessage(HttpServletRequest request, @RequestParam("groupID") int groupID)
	{
		return groupStudyingService.checkNewMessageInGroup(extractTokenToGetUsername(request), groupID);
	}
	
	@GetMapping("/findGroupbyId")
	public GroupStudyingDTO findGroupbyId(@RequestParam("groupID") int groupID)
	{
		return groupStudyingService.findGroupbyID(groupID);
	}
	
	@GetMapping("/getGroupByDocumentID")
	public GroupStudyingDTO getGroupByDocumentID(@RequestParam("documentID") int documentID)
	{
		return groupStudyingService.getGroupByDocumentID(documentID);
	}

	@GetMapping("/findGroupbyName")
	public List<GroupStudying> findGroupbyName(@RequestParam("nameGroup") String nameGroup, HttpServletRequest request)
	{
		return groupStudyingService.findGroupbyName(nameGroup, extractTokenToGetUsername(request));
	}
	
	@GetMapping("/getUserAddInGroup")
	public List<User> getUserAddInGroup(@RequestParam("groupID") int groupID, HttpServletRequest request)
	{
		return groupStudyingService.getUserToAddInGroup(groupID, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/createGroup")
	public int createGroup(HttpServletRequest request, @RequestParam("nameGroup") String nameGroup, @RequestParam("passWord") String passWord, @RequestParam("image") String image, @RequestParam("topics") List<Integer> topics)
	{
		return groupStudyingService.createGroup(extractTokenToGetUsername(request), nameGroup, passWord, image, topics);
	}
	
	@GetMapping("/getNameGroupByNotificationID")
	public String getNameGroupByNotificationID(@RequestParam("notificationID") int id)
	{
		return groupStudyingService.getNameGroupByNotificationID(id);
	}
	
	@GetMapping("/getLastMessageOfGroup")
	public String getLastMessageOfGroup(@RequestParam("groupID") int groupID)
	{
		return groupStudyingService.getLastMessageOfGroup(groupID);
	}
	
	@GetMapping("/getAllGroupByTopics")
	public List<GroupStudying> getAllGroupByTopics(HttpServletRequest request, @RequestParam("topics") List<Integer> topics)
	{
		return groupStudyingService.filterGroupByTopics(extractTokenToGetUsername(request), topics);
	}
	
	@GetMapping("/getAllGroupByTopic")
	public List<GroupStudying> getAllGroupByTopics(HttpServletRequest request, @RequestParam("topic") Integer topic)
	{
		return groupStudyingService.filterGroupByTopic(extractTokenToGetUsername(request), topic);
	}
	
	@GetMapping("/getAllRecommendedGroup")
	public List<GroupStudying> getAllRecommendedGroup(HttpServletRequest request)
	{
		return groupStudyingService.getRecommendedGroup(extractTokenToGetUsername(request));
	}
	
	@PutMapping("/updateGroup")
	public int updateGroup(@RequestBody GroupStudying group)
	{
		return groupStudyingService.updateGroup(group);
	}
	
	@PutMapping("/updateTopics")
	public int updateTopics(@RequestParam("groupID") int groupID, @RequestParam("topics") List<Integer> topics)
	{
		return groupStudyingService.updateTopics(groupID, topics);
	}
	
	@DeleteMapping("/deleteGroup")
	public void quitGroup(HttpServletRequest request, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.quitGroup(extractTokenToGetUsername(request), groupID);
	}
	
	@DeleteMapping("/deleteUser")
	public void deleteUser(@RequestParam("userName") String userName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.deleteUser(userName, groupID);
	}
	
	@PutMapping("/changeAvatarGroup")
	public void changeAvatarGroup(@RequestParam("image") MultipartFile image, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.changeAvatarGroup(image, groupID);
	}
	
	@PutMapping("/changeLeaderofGroup")
	public void changeLeaderofGroup(HttpServletRequest request, @RequestParam("newUserName") String newUserName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.changeLeaderofGroup(extractTokenToGetUsername(request), newUserName, groupID);
	}
	
	@PostMapping("/joinInGroup")
	public void joinInGroup(HttpServletRequest request, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.joinInGroup(extractTokenToGetUsername(request), groupID);
	}
	
	@PostMapping("/addFriendInGroup")
	public void joinInGroup(@RequestParam("friendUserName") String friendUserName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.joinInGroup(friendUserName, groupID);
	}
	
	@GetMapping("/getAllUserInGroup")
	public List<User> getAllUserInGroup(@RequestParam("groupID") int groupID)
	{
		return groupStudyingService.getAllUserInGroup(groupID);
	}
}