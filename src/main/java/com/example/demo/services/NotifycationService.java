package com.example.demo.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.NotifycationDTO;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationStatus;
import com.example.demo.entities.NotifycationType;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class NotifycationService {

	@Autowired
	private NotifycationRepository notifycationRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void createNotifycation(int groupID, String userName, Notifycation notifycation)
	{
		try
		{
			var user = userRepository.getById(userName);
			var group = groupStudyingRepository.getById(groupID);
			notifycation.setGroupStudying(group);
			notifycation.setNotifycationType(NotifycationType.user);
			group.getNotifycations().add(notifycation);
			group.setLastTimeEdited(new Date());
			for (var p: group.getUsers())
			{
				notifycation.getUsers().add(p);
				notifycation.getUserSeenNotifycation().add(p);
				p.getNotifycations().add(notifycation);
			}
			notifycation.setDateSent(new Date());
			notifycationRepository.save(notifycation);
			userRepository.save(user);
			groupStudyingRepository.save(group);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public List<Notifycation> getAllNotifycationByUserName(String userName)
	{
		try
		{
			return userRepository.getById(userName).getNotifycations()
					.stream().sorted((n1,n2) -> n1.getDateSent().compareTo(n2.getDateSent())).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Notifycation> getAllNotifycationByGroupID(int groupID)
	{
		try
		{
			return groupStudyingRepository.getById(groupID).getNotifycations()
					.stream().sorted((n1,n2) -> n1.getDateSent().compareTo(n2.getDateSent())).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public NotifycationDTO loadNotifycation(String userName, int notifycationID)
	{
		var user = userRepository.getById(userName);
		var notifycation = notifycationRepository.getById(notifycationID);
		
		notifycation.getUserSeenNotifycation().remove(user);
		notifycationRepository.save(notifycation);
		
		return new NotifycationDTO(notifycation);
	}
	
	public boolean checkNewNotifycation(String userName, int notifycationID)
	{
		return notifycationRepository.getById(notifycationID).getUserSeenNotifycation()
				.stream().anyMatch(p -> p.getUserName().equals(userName));
	}
	
	public List<Notifycation> findNotifycation(String userName, String inputContentbyUser)
	{
		return userRepository.getById(userName).getNotifycations().stream()
				.filter(p -> p.getContent().contains(inputContentbyUser) || p.getHeader().contains(inputContentbyUser))
				.sorted((n1,n2) -> n1.getDateSent().compareTo(n2.getDateSent())).collect(Collectors.toList());
	}
	
	public void deleteNotifycationByLeaderGroupForAll(String userName, int notifycationID, int groupID)
	{
		var user = userRepository.getById(userName);
		var notifycation = notifycationRepository.getById(notifycationID);
		var group = groupStudyingRepository.getById(groupID);
		
		if (notifycation.getGroupStudying().equals(group) && group.getLeaderOfGroup().equals(user) && group.getLeaderOfGroup().getUserName().equals(user.getUserName()))
		{
			group.getNotifycations().remove(notifycation);
			notifycation.setGroupStudying(null);
			for (var p: notifycation.getUsers())
			{
				p.getNotifycations().remove(notifycation);
			}
			notifycation.setUsers(null);
			notifycation.setUserSeenNotifycation(null);
			
			groupStudyingRepository.save(group);
			userRepository.save(user);
			notifycationRepository.delete(notifycation);
		}
		else
		{
			System.out.println("Bạn không phải trưởng nhóm nên không thể xoá");
		}
	}
	
	public void deleteNotifycationForMyAccount(String userName, int notifycationID, int groupID)
	{
		var user = userRepository.getById(userName);
		var notifycation = notifycationRepository.getById(notifycationID);
		var group = groupStudyingRepository.getById(groupID);
		
		if (notifycation.getGroupStudying().getGroupID() == group.getGroupID())
		{
			notifycation.getUsers().remove(user);
			user.getNotifycations().remove(notifycation);
			
			if (notifycation.getUsers().size() >= 1)
			{
				userRepository.save(user);
				notifycationRepository.save(notifycation);
			}
			else
			{
				notifycation.setUsers(null);
				notifycation.setUserSeenNotifycation(null);
				group.getNotifycations().remove(notifycation);
				notifycation.setGroupStudying(null);
				
				groupStudyingRepository.save(group);
				userRepository.save(user);
				notifycationRepository.delete(notifycation);
			}
		}
		else
		{
			System.out.println("Xoá không thành công");
		}
	}
}
