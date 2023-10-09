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
	private InformationRepository informationRepository;
	
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
		
	public String CreateAccount(User user)
	{
		try 
		{
			Information information = new Information();
			informationRepository.save(information);
			user.setInformation(information);
			userRepository.save(user); 
			return user.getUserName();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "Không tạo thành công"; // Failed to create account
		}
	}
	
	public void changePassword(User user)
	{
		userRepository.save(user);
	}
	
	public void changeAvatar(User user)
	{
		userRepository.save(user);
	}
	
	public User GetUserByUserName(String userName)
	{
		return userRepository.getById(userName);
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
	
	public int sendOTPtoEmail(String email) 
	{
        int otp = generateRandomNumber();
        return sendOTPtoAuthenticate(email, otp);
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
	
	private int sendOTPtoAuthenticate(String email, int otp) 
	{	
		try
		{
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("phamduythong600@gmail.com");
	        message.setTo(email);
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
