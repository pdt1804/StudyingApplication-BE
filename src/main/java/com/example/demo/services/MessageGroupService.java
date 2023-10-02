package com.example.demo.services;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.MessageGroup;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class MessageGroupService {

	@Autowired
	private MessageGroupRepository messageGroupRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	public long sendMessage(MessageGroup mess, int groupID, String userName)
	{
		try
		{
			var group = groupStudyingRepository.getById(groupID);
			var user = userRepository.getById(userName);
			
			mess.setGroup(group);
			mess.setUser(user);
			mess.setDateSent(new Date());
			messageGroupRepository.save(mess);
			
			group.getMessages().add(mess);
			groupStudyingRepository.save(group);
			
			return mess.getID();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<MessageGroup> loadMessage(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getMessages()
				.stream().sorted((d1,d2) -> d2.getDateSent().compareTo(d1.getDateSent())).collect(Collectors.toList());
	}
	
}