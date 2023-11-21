package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.RecoveryCodeService;
import com.example.demo.services.UserService;


// https://localhost:8080/api/v1/user/Authenticate
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RecoveryCodeService recoveryCodeService;
	
	@PostMapping("/test")
	public String test()
	{
		return "admin";
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/Authenticate")
	public String Authenticate(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		System.out.println(userName);
		System.out.println(passWord);


		return userService.Login(userName, passWord);
	}
	
	@PostMapping("/CreateAccount")
	public String CreateAccount(@RequestBody User user)
	{
		return userService.CreateAccount(user);
	}
	
	@GetMapping("/GetRecoveryCode")
	public int GetRecoveryCode(@RequestParam("userName") String userName)
	{
		return userService.sendOTP(userName);
	}
	
	@GetMapping("/GetAuthenticationCode")
	public int GetAuthenticationCode(@RequestParam("email") String email)
	{
		return userService.sendOTPtoEmail(email);
	}
	
	
}
