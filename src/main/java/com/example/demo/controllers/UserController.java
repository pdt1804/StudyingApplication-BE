package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Token;
import com.example.demo.entities.Topic;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.RecoveryCodeService;
import com.example.demo.services.TopicService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// https://localhost:8080/api/v1/user/Authenticate
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private RecoveryCodeService recoveryCodeService;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@PostMapping("/test")
	public String test(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		return userName;
	}
	
	@GetMapping("/checkUserName")
	public boolean checkUserName(@RequestParam("userName") String userName)
	{
		return userService.checkUserName(userName);
	}
	
	@GetMapping("/checkEmail")
	public boolean checkEmail(@RequestParam("email") String email)
	{
		return userService.checkEmail(email);
	}
	
	@GetMapping("/Authenticate")
	public String Authenticate(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord, HttpServletRequest request, HttpServletResponse response)
	{

		String token = userService.Login(userName, passWord);
		
		if (token.equals("Failed"))
		{
			return token;
		}
		else
		{	
			return token;
		}
		
	}
	
	@PostMapping("/CreateAccount")
	public Token CreateAccount(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord, @RequestParam("email") String email, @RequestParam("image") String image)
	{
		
		var user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassWord(passWord);
		
		return userService.CreateAccount(user, image);
	}
	
	@PostMapping("/ChangePasswordAfterOTP")
	public boolean ChangePassword(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		return userService.ChangePasswordAfterOTP(userName, passWord);
	}
	
	@GetMapping("/GetRecoveryCode")
	public int GetRecoveryCode(@RequestParam("userName") String userName)
	{
		return userService.sendOTP(userName);
	}
	
	@GetMapping("/GetUser")
	public User GetUser(HttpServletRequest request)
	{
		return userService.getUser(extractTokenToGetUsername(request));
	}
	
	@GetMapping("/GetAuthenticationCode")
	public int GetAuthenticationCode(@RequestParam("email") String email)
	{
		return userService.sendOTPtoEmail(email);
	}
	
	
}
