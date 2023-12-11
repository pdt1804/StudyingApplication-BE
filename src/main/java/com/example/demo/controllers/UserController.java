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
	public String test(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		return userName;
	}
	
	@GetMapping("/Authenticate")
	public String Authenticate(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		System.out.println(userName);
		System.out.println(passWord);

		return userService.Login(userName, passWord);
	}
	
	@PostMapping("/CreateAccount")
	public String CreateAccount(@RequestBody User user)
	{
		System.out.print(user.getUserName());
		return userService.CreateAccount(user);
	}
	
	@PostMapping("/ChangePasswordAfterOTP")
	public boolean ChangePassword(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord)
	{
		return userService.ChangePasswordAfterOTP(userName, passWord);
	}
	
	@GetMapping("/GetRecoveryCode")
	public int GetRecoveryCode(@RequestParam("userName") String userName)
	{
		System.out.println(userName);
		return userService.sendOTP(userName);
	}
	
	@GetMapping("/GetAuthenticationCode")
	public int GetAuthenticationCode(@RequestParam("email") String email)
	{
		System.out.println("reach");
		return userService.sendOTPtoEmail(email);
	}
	
	
}
