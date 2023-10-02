package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.MessageUser;
import com.example.demo.services.MessageUserService;

@RestController
@RequestMapping("/api/v1/messageUser")
public class MessageUserController {

	@Autowired
	private MessageUserService messageUserService;
	
	@GetMapping("/loadMessageforUser")
	public List<MessageUser> loadMessageforUser(@RequestParam("myUserName") String myUserName, @RequestParam("toUserName") String toUserName)
	{
		return messageUserService.loadMessageInUser(myUserName, toUserName);
	}
	
	@PostMapping("/sendMessageForUser")
	public long sendMessageForUser(@RequestBody MessageUser mess, @RequestParam("fromUserName") String fromUserName, @RequestParam("toUserName") String toUserName)
	{
		return messageUserService.sendMessage(mess, fromUserName, toUserName);
	}
}
