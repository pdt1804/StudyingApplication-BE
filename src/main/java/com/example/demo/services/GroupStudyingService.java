package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.GroupStudyingDTO;
import com.example.demo.entities.GroupStudying;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class GroupStudyingService {

	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageGroupRepository messageGroupRepository;
	
	public int createGroup(String userName, String nameGroup)
	{
		GroupStudying group = new GroupStudying();
		var user = userRepository.getById(userName);
		if (user != null)
		{
			group.setDateCreated(new Date());
			group.setLeaderOfGroup(user);
			group.setNameGroup(nameGroup);
			group.setLastTimeEdited(new Date());
			group.getUsers().add(user);
			user.getGroups().add(group);
			groupStudyingRepository.save(group);
			userRepository.save(user);
		}
		return group.getGroupID();
	}
	
	public void quitGroup(String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		if (group.getUsers().size() == 1)
		{
			group.getUsers().remove(user);
			group.setLeaderOfGroup(null);
			user.getGroups().remove(group);
			userRepository.save(user);
			groupStudyingRepository.delete(group);
		}
		else
		{
			group.getUsers().remove(user);
			group.setLeaderOfGroup(group.getUsers().stream().findFirst().orElse(null));
			groupStudyingRepository.save(group);
		}
		user.getGroups().remove(group);
		userRepository.save(user);
	}
	
	public GroupStudyingDTO findGroupbyID(int id)
	{
		return new GroupStudyingDTO(groupStudyingRepository.getById(id));
	}

	public List<GroupStudying> findGroupbyName(String name)
	{
		return groupStudyingRepository.findAll().stream().filter(p -> p.getNameGroup().contains(name)).collect(Collectors.toList());
	}
	
	public int updateGroup(GroupStudying group)
	{
		try
		{
			var existingGroup = groupStudyingRepository.getById(group.getGroupID());
			existingGroup.setDateCreated(new Date());
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
	
	public void joinInGroup(String userName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		var user = userRepository.getById(userName);
		user.getGroups().add(group);
		group.getUsers().add(userRepository.getById(userName));
		groupStudyingRepository.save(group);
		userRepository.save(user);
	}
	
	public void changeAvatarGroup(MultipartFile file, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		try 
		{
			group.setImageGroup(file.getBytes());
			groupStudyingRepository.save(group);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void changeLeaderofGroup(String currentLeaderUserName, String newLeaderUserName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		if (group.getLeaderOfGroup().getUserName().equals(currentLeaderUserName))
		{
			group.setLeaderOfGroup(userRepository.getById(newLeaderUserName));
			groupStudyingRepository.save(group);
		}
	}
	
	public List<GroupStudying> getAllGroupofUser(String myUserName)
	{
		return userRepository.getById(myUserName).getGroups()
				.stream().sorted((g1,g2) -> g1.getLastTimeEdited().compareTo(g2.getLastTimeEdited())).toList();
	}
	
	public boolean checkNewMessageInGroup(String myUserName, int groupID)
	{
		var lastMess = groupStudyingRepository.getById(groupID).getMessages()
				.stream().max((m1,m2) -> m1.getDateSent().compareTo(m2.getDateSent()));

		return lastMess.get().getStatusMessageWithUsers().stream().anyMatch(p -> p.getUserName().equals(myUserName));

	}
}