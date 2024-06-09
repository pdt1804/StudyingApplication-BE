 package com.example.demo.services;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.CloudinaryService;
import com.example.demo.entities.Information;
import com.example.demo.entities.Topic;
import com.example.demo.entities.User;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.InformationManagement;

@Service
public class InformationService implements InformationManagement {

	@Autowired
	private InformationRepository informationRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TopicService topicService;
	
	@Override
	public String changeAvatarCloud(MultipartFile image, String userName)
	{
		return cloudinaryService.uploadImageAvatar(image, userName);
	}
	
	public List<User> getAllChatbots()
	{
		return userRepository.findAll().stream().filter(p -> p.getUserName().contains("Chatbot")).toList();
	}
	
	@Override
	public Information getInformationbyID(int ID)
	{
		var listInformation = informationRepository.findAll();
		for (var i : listInformation)	
		{
			if (i.getInfoID() == ID)
			{
				return informationRepository.getById(ID);				
			}
		}
		return null;
	}
	
	@Override
	public Information getInformationbyUsername(String userName)
	{
		for (var p : informationRepository.findAll())
		{
			if (p.getInfoID() == userService.getUser(userName).getInformation().getInfoID())
			{
				return p;
			}
		}
		return null;
	}
	
	@Override
	public Information createInformation()
	{
		return informationRepository.save(new Information());
	}
	
	@Override
	public void updateInformation(Information information, String userName, String email)
	{
		var existingInformation = getInformationbyID(information.getInfoID());
		existingInformation.setFulName(information.getFulName());
		existingInformation.setYearOfBirth(information.getYearOfBirth());
		existingInformation.setDescription(information.getDescription());
		existingInformation.setGender(information.getGender());
		existingInformation.setPhoneNumber(information.getPhoneNumber());
		informationRepository.save(existingInformation);
		
		var user = userRepository.getById(userName);
		user.setEmail(email);
		userRepository.save(user);
	}
	
// VERSION CŨ, VẪN CÓ THỂ SỬ DỤNG SAU NÀY
//	@Override 
//	public void Initialize(int yearOfBirth, int phoneNumber, String gender, List<Topic> topics, int infoID)
//	{
//		var existingInformation = getInformationbyID(infoID);
//		existingInformation.setYearOfBirth(yearOfBirth);
//		existingInformation.setGender(gender);
//		existingInformation.setPhoneNumber(phoneNumber);
//		existingInformation.setTopics(topics);
//		informationRepository.save(existingInformation);
//	} 
	
	@Override
	public void Initialize(int yearOfBirth, int phoneNumber, String gender, String description, List<Integer> topics, int infoID)
	{
		var existingInformation = getInformationbyID(infoID);
		existingInformation.setYearOfBirth(yearOfBirth);
		existingInformation.setGender(gender);
		existingInformation.setDescription(description);
		existingInformation.setPhoneNumber(phoneNumber);
		for (var p : topics)
		{
			var topic = topicService.GetTopic(p);
			existingInformation.getTopics().add(topic);
			topic.getUsers().add(existingInformation);
			topicService.AddTopic(topic);
		}
		informationRepository.save(existingInformation);
	}
	
	@Override
	public void AddTopic(List<Integer> topics, int infoID)
	{
		var existingInformation = getInformationbyID(infoID);
		for (var p : topics)
		{
			var topic = topicService.GetTopic(p);
			existingInformation.getTopics().add(topic);
			topic.getUsers().add(existingInformation);
			topicService.AddTopic(topic);
		}
		informationRepository.save(existingInformation);
	}
	
	@Override
	public void RemoveTopic(List<Integer> topics, int infoID)
	{
		var existingInformation = getInformationbyID(infoID);
		for (var p : topics)
		{
			var topic = topicService.GetTopic(p);
			existingInformation.getTopics().remove(topic);
			topic.getUsers().remove(existingInformation);
			topicService.AddTopic(topic);
		}
		informationRepository.save(existingInformation);
	}
	
	@Override
	public String changePassword(String userName, String newPassWord, String currentPassWord)
	{
		User user = userService.GetUserByUsername(userName);
		
		if (encoder.matches(currentPassWord, user.getPassWord()))
		{
			user.setPassWord(encoder.encode(newPassWord));
		    userService.changePassword(user);
		    return "Successful";
		}
		else
		{
			return "Failed";
		}
		
	}
	
	@Override
	public String changeAvatar(String file, String userName)
	{
		try
		{
			User user = userService.GetUserByUsername(userName);
			if (user == null)
			{
				return "Không tồn tại tài khoản";
			}
			else
			{
				user.getInformation().setImage(file);
				userService.changeAvatar(user);
				System.out.println("thanhf coong");
				return "Thay đổi ảnh thành công";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("that bai");
			return "Thay đổi không thành công";
		}
	}
}
