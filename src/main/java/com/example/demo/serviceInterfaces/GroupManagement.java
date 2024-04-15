package com.example.demo.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.GroupStudyingDTO;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.User;

public interface GroupManagement {

	public GroupStudyingDTO getGroupByDocumentID (int documentID);
	
	public void deleteUser(String userName, int groupID);
	
	public List<User> getAllUserInGroup(int id);
	
	public String getNameGroupByNotificationID(int id);
	
	public int createGroup(String userName, String nameGroup, String passWord, String image, List<Integer> topics);
	
	public long sendMessage(MessageGroup mess, int groupID, String userName);
	
	public void quitGroup(String userName, int groupID);
	
	public GroupStudyingDTO findGroupbyID(int id);

	public List<GroupStudying> findGroupbyName(String nameGroup, String userName);
	
	public int updateGroup(GroupStudying group);
	
	public void joinInGroup(String userName, int groupID);
	
	public void changeAvatarGroup(MultipartFile file, int groupID);
	
	public void changeLeaderofGroup(String currentLeaderUserName, String newLeaderUserName, int groupID);
	
	public List<User> getUserToAddInGroup(int GroupID, String userName);
	
	public List<GroupStudying> getAllGroupofUser(String myUserName);
	
	public boolean checkNewMessageInGroup(String myUserName, int groupID);
	
	public int updateTopics(int groupID, List<Integer> newTopics);
	
	public List<GroupStudying> filterGroupByTopics(String userName, List<Integer> topics);
	
	public List<GroupStudying> getRecommendedGroup(String myUserName);
}
