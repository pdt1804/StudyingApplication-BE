package com.example.demo.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Information;
import com.example.demo.entities.User;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InformationService informationService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public String Login(String userName, String passWord)
	{
		List<User> listUser = userRepository.findAll();
		if (listUser.stream().anyMatch(user -> (user.getUserName().equals(userName) && user.getPassWord().equals(passWord))))
		{
			return userName;
		}
		else
		{
			return "Sai tài khoản hoặc mật khẩu";
		}
	}
		
	public int CreateAccount(User user)
	{
		try 
		{
			user.setInformation(informationService.createInformation());
			userRepository.save(user); 
			return user.getUID();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1; // Failed to create account
		}
	}
	
	public User GetUserById(int ID)
	{
		return userRepository.getById(ID);
	}
	
	public User GetUserByUsername(String userName)
	{
		return userRepository.findAll().stream().filter(user -> user.getUserName().equals(userName)).findFirst().orElse(null);
	}
	
	public int sendOTP(String userName) 
	{
        int otp = generateRandomNumber();
        return sendOTPByEmail(userName, otp);
    }
	
	private int generateRandomNumber() 
	{
        return new Random().nextInt(900000) + 100000;
    }
	
	private int sendOTPByEmail(String userName, int otp) 
	{	
		try
		{
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("phamduythong600@gmail.com");
	        message.setTo(GetUserByUsername(userName).getEmail());
	        message.setSubject("OTP Verification");
	        message.setText("Your OTP is: " + String.valueOf(otp));
	        javaMailSender.send(message);
	        return otp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
    }
}
