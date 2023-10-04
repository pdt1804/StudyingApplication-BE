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
import com.example.demo.services.GroupStudyingService;

@RestController
@RequestMapping("/api/v1/groupStudying")
public class GroupStudyingController {
	
	@Autowired
	private GroupStudyingService groupStudyingService;
	
	@GetMapping("/getAllGroupofUser")
	public List<GroupStudying> getAllGroupofUser(@RequestParam("myUserName") String myUserName)
	{
		return groupStudyingService.getAllGroupofUser(myUserName);
	}
	
	@PostMapping("/checkNewMessage")
	public boolean checkNewMessage(@RequestParam("myUserName") String myUserName, @RequestParam("groupID") int groupID)
	{
		return groupStudyingService.checkNewMessageInGroup(myUserName, groupID);
	}
	
	@GetMapping("/findGroupbyId")
	public GroupStudyingDTO findGroupbyId(@RequestParam("groupID") int groupID)
	{
		return groupStudyingService.findGroupbyID(groupID);
	}

	@GetMapping("/findGroupbyName")
	public List<GroupStudying> findGroupbyName(@RequestParam("nameGroup") String nameGroup)
	{
		return groupStudyingService.findGroupbyName(nameGroup);
	}
	
	@PostMapping("/createGroup")
	public int createGroup(@RequestParam("userName") String userName, @RequestParam("nameGroup") String nameGroup)
	{
		return groupStudyingService.createGroup(userName, nameGroup);
	}
	
	@PutMapping("/updateGroup")
	public int updateGroup(@RequestBody GroupStudying group)
	{
		return groupStudyingService.updateGroup(group);
	}
	
	@DeleteMapping("/deleteGroup")
	public void quitGroup(@RequestParam("myUserName") String myUserName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.quitGroup(myUserName, groupID);
	}
	
	@PutMapping("/changeAvatarGroup")
	public void changeAvatarGroup(@RequestParam("file") MultipartFile file, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.changeAvatarGroup(file, groupID);
	}
	
	@PutMapping("/changeLeaderofGroup")
	public void changeLeaderofGroup(@RequestParam("currentUserName") String currentUserName, @RequestParam("newUserName") String newUserName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.changeLeaderofGroup(currentUserName, newUserName, groupID);
	}
	
	@PostMapping("/joinInGroup")
	public void joinInGroup(@RequestParam("myUserName") String myUserName, @RequestParam("groupID") int groupID)
	{
		groupStudyingService.joinInGroup(myUserName, groupID);
	}
}