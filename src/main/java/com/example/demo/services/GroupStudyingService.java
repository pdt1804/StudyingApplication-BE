package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.DTO.GroupStudyingDTO;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.User;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.GroupManagement;

@Service
public class GroupStudyingService implements GroupManagement {

	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private MessageGroupRepository messageGroupRepository;
	
	@Autowired
	private NotifycationRepository notifycationRepository;
	
	@Autowired
	private FriendShipService friendShipService;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Override
	public GroupStudyingDTO getGroupByDocumentID (int documentID)
	{
		return new GroupStudyingDTO(documentRepository.getById(documentID).getGroup());
	}

	@Override
	public void deleteUser(String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		
		user.getGroups().remove(group);
		group.getUsers().remove(user);
						
		var notification = new Notifycation();
		notification.setDateSent(new Date());
		notification.setContent("Bạn đã bị nhóm trưởng nhóm " + group.getNameGroup() + " mời khỏi nhóm");
		notification.setGroupStudying(null);
		notification.setHeader("Thông báo mời ra khỏi nhóm " + group.getNameGroup());
		notification.setNotifycationType(NotifycationType.admin);
		notification.getUsers().add(user);
		notification.setGroupStudying(group);
		notification.getUserSeenNotifycation().add(user);
		user.getNotifycations().add(notification);
		
		group.getNotifycations().add(notification);
		
		notifycationRepository.save(notification);
		groupStudyingRepository.save(group);
		userRepository.save(user);
		
	}

	@Override
	public List<User> getAllUserInGroup(int id)
	{
		return groupStudyingRepository.getById(id).getUsers();
	}

	@Override
	public String getNameGroupByNotificationID(int id)
	{
		var notification = notifycationRepository.getById(id);
		return notification.getGroupStudying().getNameGroup(); 
	}

	@Override
	public int createGroup(String userName, String nameGroup, String passWord, String image)
	{
		GroupStudying group = new GroupStudying();
		var user = userRepository.getById(userName);
		if (user != null)
		{
			group.setDateCreated(new Date());
			group.setLeaderOfGroup(user);
			group.setNameGroup(nameGroup);
			group.setPassWord(passWord);
			group.setLastTimeEdited(new Date());
			group.setImageGroup(image);
			group.getUsers().add(user);
			user.getGroups().add(group);
			groupStudyingRepository.save(group);
			userRepository.save(user);
			
			MessageGroup mess = new MessageGroup();
			mess.setContent("Xin chào, mình là nhóm trưởng");
			sendMessage(mess, group.getGroupID(), userName);
		}
		return group.getGroupID();
	}

	@Override
	public long sendMessage(MessageGroup mess, int groupID, String userName)
	{
		try
		{
			var group = groupStudyingRepository.getById(groupID);
			var user = userRepository.getById(userName);
			
			for (var p : group.getUsers())
			{
				mess.getStatusMessageWithUsers().add(p);
			}
			
			mess.getStatusMessageWithUsers().remove(user);
			mess.setGroup(group);
			mess.setUser(user);
			mess.setDateSent(new Date());
			messageGroupRepository.save(mess);
			
			group.getMessages().add(mess);
			group.setLastTimeEdited(new Date());
			groupStudyingRepository.save(group);
			
			return mess.getID();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void quitGroup(String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		
		user.getGroups().remove(group);
		group.getUsers().remove(user);
		
		groupStudyingRepository.save(group);
		userRepository.save(user);
	}

	@Override
	public GroupStudyingDTO findGroupbyID(int id)
	{
		return new GroupStudyingDTO(groupStudyingRepository.getById(id));
	}

	@Override
	public List<GroupStudying> findGroupbyName(String nameGroup, String userName)
	{
		var listGroup = groupStudyingRepository.findAll().stream().filter(p -> p.getNameGroup().toLowerCase().contains(nameGroup.toLowerCase()) && p.getUsers().size() > 0).collect(Collectors.toList());
		listGroup.removeAll(userRepository.getById(userName).getGroups());
		
		return listGroup;
	}

	@Override
	public int updateGroup(GroupStudying group)
	{
		try
		{
			var existingGroup = groupStudyingRepository.getById(group.getGroupID());
			existingGroup.setDateCreated(new Date());
			existingGroup.setPassWord(group.getPassWord());
			existingGroup.setNameGroup(group.getNameGroup());
			groupStudyingRepository.save(existingGroup);
			return existingGroup.getGroupID();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void joinInGroup(String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		user.getGroups().add(group);
		group.getUsers().add(userRepository.getById(userName));
		groupStudyingRepository.save(group);
		userRepository.save(user);
	}

	@Override
	public void changeAvatarGroup(MultipartFile image, int groupID)
	{
		try 
		{
	        Map<String, String> data = this.cloudinary.uploader().upload(image.getBytes(), Map.of());
			var group = groupStudyingRepository.getById(groupID);
			if (group.getPublicID() != null)
			{
				this.cloudinary.uploader().destroy(group.getPublicID(), ObjectUtils.asMap("type", "upload", "resource_type", "image"));
			}
			group.setImageGroup(data.get("url"));
			group.setPublicID(data.get("public_id"));
			groupStudyingRepository.save(group);
			System.out.println("finish");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void changeLeaderofGroup(String currentLeaderUserName, String newLeaderUserName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		if (group.getLeaderOfGroup().getUserName().equals(currentLeaderUserName))
		{
			group.setLeaderOfGroup(userRepository.getById(newLeaderUserName));
			groupStudyingRepository.save(group);
		}
	}

	@Override
	public List<User> getUserToAddInGroup(int GroupID, String userName)
	{
		var listFriend = friendShipService.getAllFriendofUser(userName);
		var group = groupStudyingRepository.getById(GroupID);
		
		for (var p : group.getUsers())
		{
			if (listFriend.stream().anyMatch(s -> s.getUserName().equals(p.getUserName())))
			{
				listFriend.remove(p);
			}
		}
		
		return listFriend;
	}

	@Override
	public List<GroupStudying> getAllGroupofUser(String myUserName)
	{
		return userRepository.getById(myUserName).getGroups()
				.stream().sorted((g1,g2) -> g2.getLastTimeEdited().compareTo(g1.getLastTimeEdited())).toList();
	}

	@Override
	public boolean checkNewMessageInGroup(String myUserName, int groupID)
	{
		var lastMess = groupStudyingRepository.getById(groupID).getMessages()
				.stream().max((m1,m2) -> m1.getDateSent().compareTo(m2.getDateSent()));

		return lastMess.get().getStatusMessageWithUsers().stream().anyMatch(p -> p.getUserName().equals(myUserName));

	}
}