package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.MessageGroupPayload;
import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.User;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.MessageGroupService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/messagegroup")
public class MessageGroupController {

	@Autowired
	private MessageGroupService messageGroupService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@PostMapping("/sendMessage")
	public long sendMessage(@RequestParam("messContent") String content, @RequestParam("groupID") int groupID, HttpServletRequest request)
	{
		var mess = new MessageGroup();
		mess.setContent(content);

		return messageGroupService.sendMessage(mess, groupID, extractTokenToGetUsername(request));
	}
	
	@MessageMapping("/sendMess")
	@SendTo("/public/queue")
	public void sendMessage(@Payload MessageGroupPayload message)
	{
		//System.out.println(message.getGroupID());
		messagingTemplate.convertAndSend("/public/queue", "successful");
		//messagingTemplate.convertAndSend("/public/queue/null" + message.getGroupID(), "successful");

		//System.out.println("Sent successful: " + "/public/queue/" + message.getGroupID());
	}
	
	@GetMapping("/loadMessageInGroup")
	public List<MessageGroup> loadMessageInGroup(HttpServletRequest request, @RequestParam("groupID") int groupID)
	{
		return messageGroupService.loadMessage(extractTokenToGetUsername(request), groupID);
	}
	
	@GetMapping("/getSentUserInGroup")
	public User getSentUserInGroup(@RequestParam("messID") long id)
	{
		return messageGroupService.getSentUserInGroup(id);
	}
}
