package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RecoveryCodeService recoveryCodeService;
	
	@PostMapping("/Authenticate")
	public String Authenticate(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		return userService.Login(userName, passWord);
	}
	
	@PostMapping("/CreateAccount")
	public String CreateAccount(@RequestBody User user)
	{
		return userService.CreateAccount(user);
	}
	
	@GetMapping("/GetRecoveryCode")
	public int GetCode(@RequestParam("userName") String userName)
	{
		return userService.sendOTP(userName);
	}
	
	
}
