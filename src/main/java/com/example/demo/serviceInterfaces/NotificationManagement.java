package com.example.demo.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.NotifycationDTO;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;

public interface NotificationManagement {

	public int createNotifycation(int groupID, String userName, Notifycation notifycation);

	public void insertImage(int notificationID, MultipartFile image);
	
	public List<Notifycation> getAllNotifycationByUserName(String userName);
	
	public List<Notifycation> getAllNotifycationByGroupID(int groupID);
	
	public NotifycationDTO loadNotifycation(String userName, int notifycationID);
	
	public boolean checkNewNotifycation(String userName, int notifycationID);
	
	public List<Notifycation> findNotifycation(String userName, String inputContentbyUser);
	
	public String deleteNotifycationByLeaderGroupForAll(String userName, int notifycationID, int groupID);
	
	public void deleteNotifycationForMyAccount(String userName, int notifycationID, int groupID);
}
