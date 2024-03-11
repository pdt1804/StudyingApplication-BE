package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Information;
import com.example.demo.entities.User;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.AccountManagement;
import com.example.demo.serviceInterfaces.RecoveryAccount;

@Service
public class UserService implements AccountManagement, RecoveryAccount {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InformationRepository informationRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean checkUserName(String userName)
	{
		var list = userRepository.findAll();
		for (var p : list)
		{
			if (p.getUserName().equals(userName))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean checkEmail(String email)
	{
		var list = userRepository.findAll();
		for (var p : list)
		{
			if (p.getEmail().equals(email))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String Login(String userName, String passWord)
	{
		try
		{	

			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, passWord));
			
			if (authenticate.isAuthenticated())
			{
				return jwtService.generateToken(userName);
			}
			else
			{
				return "Failed";
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "Failed";
		}
	}
		
	@Override
	public String CreateAccount(User user, String image)
	{
		try 
		{
			boolean checkExist = userRepository.findAll().stream().anyMatch(p -> p.getUserName().toLowerCase().equals(user.getUserName().toLowerCase()));

			if (checkExist == false)
			{
				Information information = new Information();
				information.setImage(image);
				information.setFulName("user" + userRepository.findAll().size() + 1);
				informationRepository.save(information);
				user.setInformation(information);
				user.setPassWord(passwordEncoder.encode(user.getPassWord()));
				userRepository.save(user); 
				return user.getUserName();
			}
			else
			{
				return "Không tạo thành công";
			}
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
	
	@Override
	public int sendOTP(String userName) 
	{
        int otp = generateRandomNumber();
        return sendOTPByEmail(userName, otp);
    }
	
	@Override
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
	        System.out.println(GetUserByUsername(userName).getEmail());
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
	
	@Override
	public User getUser(String userName)
	{
		for (var p : userRepository.findAll())
		{
			if (p.getUserName().equals(userName))
			{
				return p;
			}
		}
		return null;
	}

	public boolean ChangePasswordAfterOTP(String userName, String passWord) 
	{
		var user = userRepository.getById(userName);
		
		if (user != null)
		{
			user.setPassWord(passwordEncoder.encode(passWord));
			userRepository.save(user);
			return true;
		}
		else
		{
			return false;
		}	
	}
}
