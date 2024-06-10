package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import jakarta.annotation.PostConstruct;

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

import com.example.demo.DTO.Token;
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
	public Token CreateAccount(User user, String image)
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
				return new Token(jwtService.generateToken(user.getUserName()), information.getInfoID());
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null; // Failed to create account
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
	
	@PostConstruct
	private void SettingUpChatbots()
	{
		var checkExist = userRepository.findAll().stream().anyMatch(p -> p.getUserName().contains("Chatbot"));
		
		if (checkExist == false)
		{
			var chatbotGemini = new User();
			chatbotGemini.setUserName("Chatbot-Gemini");
			chatbotGemini.setInformation(informationRepository.save(new Information()));
			chatbotGemini.getInformation().setFulName("Gemini AI");
			chatbotGemini.setEmail(UUID.randomUUID().toString());
			chatbotGemini.getInformation().setImage("https://s.cafebazaar.ir/images/icons/com.google.android.apps.bard-cb4d9ab8-803b-45f7-9333-ac326c276662_512x512.png?x-img=v1/resize,h_256,w_256,lossless_false/optimize");
			informationRepository.save(chatbotGemini.getInformation());
			userRepository.save(chatbotGemini);
			
			var chatbotApplication = new User();
			chatbotApplication.setUserName("Chatbot-Application");
			chatbotApplication.setInformation(informationRepository.save(new Information()));
			chatbotApplication.getInformation().setFulName("Chatbot Application");
			chatbotApplication.setEmail(UUID.randomUUID().toString());
			chatbotApplication.getInformation().setImage("https://icon-library.com/images/bot-icon/bot-icon-5.jpg");
			informationRepository.save(chatbotApplication.getInformation());
			userRepository.save(chatbotApplication);	
			
			var chatbotOpenAI = new User();
			chatbotOpenAI.setUserName("Chatbot-OpenAI");
			chatbotOpenAI.setInformation(informationRepository.save(new Information()));
			chatbotOpenAI.getInformation().setFulName("Chatbot GPT");
			chatbotOpenAI.setEmail(UUID.randomUUID().toString());
			chatbotOpenAI.getInformation().setImage("https://static.vecteezy.com/system/resources/previews/021/608/795/non_2x/chatgpt-logo-chat-gpt-icon-on-green-background-free-vector.jpg");
			informationRepository.save(chatbotOpenAI.getInformation());
			userRepository.save(chatbotOpenAI);
		
			var chatbotJasperAI = new User();
			chatbotJasperAI.setUserName("Chatbot-JasperAI");
			chatbotJasperAI.setInformation(informationRepository.save(new Information()));
			chatbotJasperAI.getInformation().setFulName("Chatbot Jasper");
			chatbotJasperAI.setEmail(UUID.randomUUID().toString());
			chatbotJasperAI.getInformation().setImage("https://cdn.prod.website-files.com/60e5f2de011b865a06c30ddd/65157fcb687da6b4bb42085f_Jasper%20Author-min.png");
			informationRepository.save(chatbotJasperAI.getInformation());
			userRepository.save(chatbotJasperAI);
			
			var chatbotWitAI = new User();
			chatbotWitAI.setUserName("Chatbot-WitAI");
			chatbotWitAI.setInformation(informationRepository.save(new Information()));
			chatbotWitAI.getInformation().setFulName("Chatbot Wit AI");
			chatbotWitAI.setEmail(UUID.randomUUID().toString());
			chatbotWitAI.getInformation().setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRi2uwj2XTMUAXYDt-QGpsolQzIt5ZIzcJNxg&s");
			informationRepository.save(chatbotWitAI.getInformation());
			userRepository.save(chatbotWitAI);
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
