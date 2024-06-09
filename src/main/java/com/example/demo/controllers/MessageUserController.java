package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.MessageGroupPayload;
import com.example.demo.entities.MessageUser;
import com.example.demo.entities.User;
import com.example.demo.services.JwtService;
import com.example.demo.services.MessageUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/messageUser")
public class MessageUserController {

	@Autowired
	private MessageUserService messageUserService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/loadMessageforUser")
	public List<MessageUser> loadMessageforUser(HttpServletRequest request, @RequestParam("toUserName") String toUserName)
	{
		return messageUserService.loadMessageInUser(extractTokenToGetUsername(request), toUserName);
	}
	
	@GetMapping("/getFriendID")
	public int getFriendID(HttpServletRequest request, @RequestParam("toUserName") String toUserName)
	{
		return messageUserService.getFriendID(extractTokenToGetUsername(request), toUserName);
	}
	
	@GetMapping("/getSentUser")
	public User getSentUser(@RequestParam("messID") long id)
	{
		return messageUserService.getSentUser(id);
	}
	
	@GetMapping("/getGroupData")
	public ResponseEntity<String> getGroupData()
	{
		return ResponseEntity.ok(messageUserService.retrievingInformationGroup());
	}
	
	@GetMapping("/checkSender")
	public boolean checkSender(@RequestParam("userName") String userName, HttpServletRequest request)
	{
		return (userName.equals(extractTokenToGetUsername(request))) ? true : false;
	}
	
	@PostMapping("/sendMessageForUser")
	public long sendMessageForUser(@RequestParam("messContent") String content, HttpServletRequest request, @RequestParam("toUserName") String toUserName)
	{
		var mess = new MessageUser();
		mess.setContent(content);
		
		return messageUserService.sendMessage(mess, extractTokenToGetUsername(request), toUserName);
	}
	
	@PostMapping("/uploadImages")
	public void uploadImages(HttpServletRequest request, @RequestParam("toUserName") String toUserName, @RequestParam("files") List<MultipartFile> files) throws IOException
	{	
		messageUserService.UploadImageForMessage(files, extractTokenToGetUsername(request), toUserName);
	}
	
	@PostMapping("/uploadImage")
	public void uploadImage(@RequestParam("messID") long messID, @RequestParam("file") MultipartFile file, @RequestParam("width") int width, @RequestParam("height") int height) throws IOException
	{	
		messageUserService.uploadFileToCloudinary(file, messID, width, height);
	}
	
	@MessageMapping("/sendMessForUser")
	public void sendMessage(@Payload MessageGroupPayload message)
	{
		//System.out.println(message.getGroupID());
		//messagingTemplate.convertAndSend("/user/public/queue/" + message.getGroupID(), "successful");
		messagingTemplate.convertAndSend("/user/private/queue/chat/" + message.getGroupID(), "successful");
		//messagingTemplate.convertAndSend("/public/queue/null" + message.getGroupID(), "successful");

		System.out.println("Sent successful: " + "/public/queue/" + message.getGroupID());
	}
	
	@PostMapping("/saveChatbotMessage")
	public long saveChatbotMessage(@RequestParam("messContent") String content, HttpServletRequest request, @RequestParam("chatbotUserName") String chatbotUserName)
	{
		var mess = new MessageUser();
		mess.setContent(content);
		
		return messageUserService.saveChatBotMessage(mess, extractTokenToGetUsername(request), chatbotUserName);
	}
}
