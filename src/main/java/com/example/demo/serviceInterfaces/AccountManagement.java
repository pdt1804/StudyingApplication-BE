package com.example.demo.serviceInterfaces;

import java.util.List;

import com.example.demo.DTO.Token;
import com.example.demo.entities.Information;
import com.example.demo.entities.User;


public interface AccountManagement {

	public String Login(String userName, String passWord);
		
	public Token CreateAccount(User user, String image);
	
	public boolean checkUserName(String userName);
	
	public boolean checkEmail(String email);
	
	public User getUser(String userName);
}
