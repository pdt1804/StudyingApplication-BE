package com.example.demo.serviceInterfaces;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Information;
import com.example.demo.entities.Topic;
import com.example.demo.entities.User;

public interface InformationManagement {

	public Information getInformationbyID(int ID);
	
	public Information getInformationbyUsername(String userName);
	
	public Information createInformation();
	
	public void updateInformation(Information information, String userName, String email);
	
	public String changePassword(String userName, String newPassWord, String currentPassWord);
	
	public String changeAvatar(String file, String userName);
	
	public String changeAvatarCloud(MultipartFile image, String userName);
	
	public void Initialize(int yearOfBirth, int phoneNumber, String gender, String description, List<Integer> topics, int infoID);
	
	public void AddTopic(List<Integer> topics, int infoID);
	
	public void RemoveTopic(List<Integer> topics, int infoID);


}
