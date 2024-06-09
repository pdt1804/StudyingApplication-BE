package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Downside;
import com.example.demo.entities.Information;
import com.example.demo.entities.Topic;
import com.example.demo.entities.Upside;
import com.example.demo.entities.User;
import com.example.demo.services.InformationService;
import com.example.demo.services.JwtService;
import com.example.demo.services.TopicService;
import com.example.demo.services.UpsideAndDownsideService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/information")
public class InformationController {

	@Autowired
	private InformationService informationService;
	
	@Autowired
	private UpsideAndDownsideService upsideAndDownsideService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TopicService topicService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
//	@GetMapping("/getAllTopics")
//	public List<Topic> getAllTopics()
//	{
//		return topicService.getAllTopics();
//	}
	
	@GetMapping("/getAllChatbots")
	public List<User> getAllChatbots()
	{
		return informationService.getAllChatbots();
	}
	
	@GetMapping("/getAllFavoriteTopics")
	public List<Topic> getAllFavoriteTopics(@RequestParam("infoID") int infoID)
	{
		return topicService.getAllFavoriteTopics(infoID);
	}
	
	@GetMapping("/getAllTopics")
	public ResponseEntity<List<Topic>> getAllTopics()
	{
		return ResponseEntity.ok(topicService.getAllTopics());
	}
	
	@GetMapping("/getAllUnfavourateTopics")
	public List<Topic> getAllUnfavourateTopics(@RequestParam("infoID") int infoID)
	{
		return topicService.getAllUnfavourateTopics(infoID);
	}
	
	@PostMapping("/updateInformation")
	public void updateInformation(@RequestBody Information information, HttpServletRequest request, @RequestParam("email") String email)
	{
		informationService.updateInformation(information, extractTokenToGetUsername(request), email);
	}
	
	@PostMapping("/initialize")
	public void initialize(@RequestParam("yearOfBirth") int yearOfBirth, 
						   @RequestParam("gender") String gender, 
						   @RequestParam("description") String description, 
						   @RequestParam("phoneNumber") int phoneNumber, 
						   @RequestParam("topics") List<Integer> topics,
						   @RequestParam("infoID") int infoID)
	{
		System.out.println(yearOfBirth);
		informationService.Initialize(yearOfBirth, phoneNumber, gender, description, topics, infoID);
	}
	
	@PostMapping("/AddTopic")
	public void AddTopic(@RequestParam("topics") List<Integer> topics,
						 @RequestParam("infoID") int infoID)
	{
		informationService.AddTopic(topics, infoID);
	}
	
	@PostMapping("/RemoveTopic")
	public void RemoveTopic(@RequestParam("topics") List<Integer> topics,
						    @RequestParam("infoID") int infoID)
	{
		informationService.RemoveTopic(topics, infoID);
	}
	
	@GetMapping("/getInformation")
	public Information getInformationbyUsername(HttpServletRequest request)
	{
		return informationService.getInformationbyUsername(extractTokenToGetUsername(request));
	}
	
	@GetMapping("/getAvatar")
	public String getAvatar(HttpServletRequest request)
	{
		return informationService.getInformationbyUsername(extractTokenToGetUsername(request)).getImage();
    }	
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassWord") String newPassWord, @RequestParam("currentPassWord") String currentPassWord, HttpServletRequest request)
	{
		return informationService.changePassword(extractTokenToGetUsername(request), newPassWord, currentPassWord);
	}
	
	@PostMapping("/changeAvatar")
	public String changeAvatar(@RequestParam ("image") String image, HttpServletRequest request)
	{
		return informationService.changeAvatar(image, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/changeAvatarCloud")
	public String changeAvatarCloud(@RequestParam ("image") MultipartFile image, HttpServletRequest request)
	{
		return informationService.changeAvatarCloud(image, extractTokenToGetUsername(request));
	}
	
	@GetMapping("/ExtractBearerToken")
	public String ExtractBearerToken(HttpServletRequest request)
	{
		return extractTokenToGetUsername(request);
		
	}
	
	@GetMapping("/GetUser")
	public User GetUser(@RequestParam("userName") String userName)
	{
		return userService.getUser(userName);
	}
	
	@GetMapping("/getAllUpside")
	public List<Upside> getAllUpside()
	{
		return upsideAndDownsideService.getAllUpside();
	}
	
	@GetMapping("/getAllDownside")
	public List<Downside> getAllDownside()
	{
		return upsideAndDownsideService.getAllDownside();
	}
}
