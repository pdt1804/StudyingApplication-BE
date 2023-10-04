package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.MessageGroup;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.services.MessageGroupService;

@RestController
@RequestMapping("/api/v1/messagegroup")
public class MessageGroupController {

	@Autowired
	private MessageGroupService messageGroupService;
	
	@PostMapping("/sendMessage")
	public long sendMessage(@RequestBody MessageGroup mess, @RequestParam("groupID") int groupID, @RequestParam("userName") String userName)
	{
		return messageGroupService.sendMessage(mess, groupID, userName);
	}
	
	@GetMapping("/loadMessageInGroup")
	public List<MessageGroup> loadMessageInGroup(@RequestParam("myUserName") String myUserName, @RequestParam("groupID") int groupID)
	{
		return messageGroupService.loadMessage(myUserName, groupID);
	}
}
