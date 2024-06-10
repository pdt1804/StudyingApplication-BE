package com.example.demo.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
import com.example.demo.entities.Topic;
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
	private TopicService topicService;
	
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
		notification.getUserSeenNotifycation().add(user.getUserName());
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
	public int createGroup(String userName, String nameGroup, String passWord, String image, List<Integer> topics)
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
			
			for (var p : topics)
			{
				var topic = topicService.GetTopic(p);
				group.getTopics().add(topic);
				topic.getGroups().add(group);
				groupStudyingRepository.save(group);
				topicService.AddTopic(topic);
				System.out.println("reach");
			}
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
			if (group.getPassWord() != null) existingGroup.setPassWord(group.getPassWord());
			if (group.getNameGroup() != null)existingGroup.setNameGroup(group.getNameGroup());
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
	public int updateTopics(int groupID, List<Integer> newTopics)
	{
		try
		{
			var existingGroup = groupStudyingRepository.getById(groupID);
			var list = new ArrayList<>(existingGroup.getTopics());
			for(var p : list)
			{
				if (newTopics.contains(p.getTopicID()))
				{
					newTopics.remove(new Integer(p.getTopicID()));
				}
				else
				{
					var topic = topicService.GetTopic(p.getTopicID());
					topic.getGroups().remove(existingGroup);
					existingGroup.getTopics().remove(topic);
					topicService.AddTopic(topic);
					groupStudyingRepository.save(existingGroup);
				} 
			}
			
			for (var p : newTopics)
			{
				var topic = topicService.GetTopic(p);
				existingGroup.getTopics().add(topic);
				topic.getGroups().add(existingGroup);
				topicService.AddTopic(topic);
				groupStudyingRepository.save(existingGroup);
			}
			
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
	public List<GroupStudying> filterGroupByTopics(String userName, List<Integer> topics)
	{
		var filterGroups = new ArrayList<GroupStudying>();
		var allGroups = new ArrayList<>(groupStudyingRepository.findAll());
		allGroups.removeAll(userRepository.getById(userName).getGroups());
		
		for(var p : allGroups)
		{
			if (CheckGroupByTopics(p, topics))
			{
				filterGroups.add(p);
			}
		}
		
		return filterGroups;
	}
	
	public String getLastMessageOfGroup(int groupID)
	{
		var messList = groupStudyingRepository.getById(groupID).getMessages();
		var mess = messList.get(messList.size() - 1);
		return mess.getUser().getInformation().getFulName() + ": " + mess.getContent();
	}
	
	public List<GroupStudying> filterGroupByTopic(String userName, Integer topic)
	{
		var filterGroups = new ArrayList<GroupStudying>();
		var allGroups = new ArrayList<>(groupStudyingRepository.findAll());
		allGroups.removeAll(userRepository.getById(userName).getGroups());
		
		for(var p : allGroups)
		{
			if (p.getUsers().size() == 0)
			{
				continue;
			}
			
			if (CheckGroupByTopic(p, topic))
			{
				filterGroups.add(p);
			}
		}
		
		return filterGroups;
	}

	private boolean CheckGroupByTopics(GroupStudying p, List<Integer> topics) {
		int index = 0;
		for (var topic : p.getTopics())
		{
			for (var requiredTopic : topics)
			{
				if (requiredTopic == topic.getTopicID())
				{
					index++;
					break;
				}
			}
		}
		
		if (index == topics.size()) return true;
		return false;
	}
	
	private boolean CheckGroupByTopic(GroupStudying p, Integer topic) {
		for (var topicInGroup : p.getTopics())
		{
			if (topicInGroup.getTopicID() == topic)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private int NumberTopicsRelated(GroupStudying p, List<Integer> topics) {
		int index = 0;
		for (var topic : p.getTopics())
		{
			for (var requiredTopic : topics)
			{
				if (requiredTopic == topic.getTopicID())
				{
					index++;
					break;
				}
			}
		}

		return index;
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
	
	@Override
	public List<GroupStudying> getRecommendedGroup(String myUserName)
	{
		
		var allGroups = new ArrayList<>(groupStudyingRepository.findAll());
		allGroups.removeAll(userRepository.getById(myUserName).getGroups());
		
		List<GroupStudying> recommendedGroup = new ArrayList<GroupStudying>();
		recommendedGroup.addAll(recommenderSystemByFavoriteTopic(getFavoriteTopicsOfUser(myUserName), 8, allGroups));
		recommendedGroup.addAll(recommenderSystemByGroup(getMostMessageGroup(myUserName), 6, allGroups));
		recommendedGroup.addAll(recommenderSystemByGroup(getMostBlogCommentReplyGroup(myUserName), 6, allGroups));
		
		Collections.shuffle(recommendedGroup);
		return recommendedGroup;

	}
	
	private Collection<? extends GroupStudying> recommenderSystemByGroup(GroupStudying group, int i, List<GroupStudying> groups) {
		if (group == null) return new ArrayList<GroupStudying>();
		
		List<Integer> list = new ArrayList<>();
		
		for (var p : group.getTopics())
		{
			list.add(p.getTopicID());
		}
		
		return recommenderSystemByFavoriteTopic(list, i, groups);
	}

	private Collection<? extends GroupStudying> recommenderSystemByFavoriteTopic(List<Integer> favoriteTopicsOfUser, int i, List<GroupStudying> groups) {
		if (favoriteTopicsOfUser.size() == 0) return new ArrayList<GroupStudying>();
		
		final int RANGEFOR1TOPIC = 60;
		final int RANGEFOR2TOPIC = 97;
		final int RANGEFOR3TOPIC = 119;
		
		var recommendedGroup = new ArrayList<GroupStudying>();
		Collections.shuffle(groups);
		for(var p : groups)
		{
			if (p.getPassWord() == null || p.getPassWord().isBlank() || p.getPassWord().isEmpty())
			{
				int relatedTopic = NumberTopicsRelated(p, favoriteTopicsOfUser);
				int points = CaculatingPoint(p, favoriteTopicsOfUser, relatedTopic);
				
				if (relatedTopic == 1 && points >= RANGEFOR1TOPIC)
				{
					recommendedGroup.add(p);
				}
				else if (relatedTopic == 2 && points >= RANGEFOR2TOPIC)
				{
					recommendedGroup.add(p);
				}
				else if (relatedTopic == 3 && points >= RANGEFOR3TOPIC)
				{
					recommendedGroup.add(p);

				}
				
				if (recommendedGroup.size() == i)
				{
					break;
				}
			}
		}
		
		return recommendedGroup;
	}

	private int CaculatingPoint(GroupStudying group, List<Integer> favoriteTopicsOfUser, int relatedTopic) {
		int point = 0;
		
		point += PointOfTopic(group, relatedTopic);
		point += PointOfDateCreated(group);
		point += PointOfLastTimeEdited(group);
		point += PointOfUsersSize(group);
		point += PointOfQuantityDocument(group);
		point += PointOfQuantitySubject(group);
		point += PointOfQuantityBlog(group);
		point += PointOfQuantityMessage(group);
		
		return point;
	}

	private int PointOfTopic(GroupStudying group, int relatedTopic) {
		if (relatedTopic == 1)
		{
			return 30;
		}
		else if (relatedTopic == 2)
		{
			return 65;
		}
		else if (relatedTopic == 3)
		{
			return 105;
		}
		else
		{
			return 0;
		}		
	}

	private int PointOfQuantityMessage(GroupStudying group) {
		if (group.getMessages().size() <= 30)
		{
			return 3;
		}
		else if (30 < group.getMessages().size() && group.getUsers().size() <= 100)
		{
			return 6;
		}
		else if (100 < group.getMessages().size() && group.getUsers().size() <= 200)
		{
			return 14;
		}
		return 25;	
	}

	private int PointOfQuantityBlog(GroupStudying group) {
		if (group.getBlogs().size() <= 10)
		{
			return 3;
		}
		else if (10 < group.getBlogs().size() && group.getUsers().size() <= 30)
		{
			return 9;
		}
		else if (30 < group.getBlogs().size() && group.getUsers().size() <= 60)
		{
			return 20;
		}
		return 35;	
	}

	private int PointOfQuantitySubject(GroupStudying group) {
		return group.getSubjects().size();
	}

	private int PointOfQuantityDocument(GroupStudying group) {
		if (group.getDocuments().size() <= 5)
		{
			return 3;
		}
		else if (5 < group.getDocuments().size() && group.getUsers().size() <= 15)
		{
			return 9;
		}
		else if (15 < group.getDocuments().size() && group.getUsers().size() <= 30)
		{
			return 20;
		}
		return 35;	
	}

	private int PointOfUsersSize(GroupStudying group) {
		if (group.getUsers().size() <= 10)
		{
			return 2;
		}
		else if (10 < group.getUsers().size() && group.getUsers().size() <= 25)
		{
			return 5;
		}
		else if (25 < group.getUsers().size() && group.getUsers().size() <= 75)
		{
			return 18;
		}
		else if (75 < group.getUsers().size() && group.getUsers().size() <= 150)
		{
			return 40;
		}
		return 60;
	}

	private int PointOfLastTimeEdited(GroupStudying group) {
		long days = ChronoUnit.DAYS.between(LocalDate.of(group.getLastTimeEdited().getYear(), group.getLastTimeEdited().getMonth() + 1, group.getLastTimeEdited().getDate()), LocalDate.of(new Date().getYear(), new Date().getMonth(), new Date().getDate()));
		if (days <= 2)
		{
			return 2;
		}
		else if (2 < days && days <= 5)
		{
			return 1;
		}
		return 0;
	}

	private int PointOfDateCreated(GroupStudying group) {
		long days = ChronoUnit.DAYS.between(LocalDate.of(group.getDateCreated().getYear(), group.getDateCreated().getMonth() + 1, group.getDateCreated().getDate()), LocalDate.of(new Date().getYear(), new Date().getMonth(), new Date().getDate()));
		if (days < 90)
		{
			return 2;
		}
		else if (90 <= days && days < 180)
		{
			return 4;
		}
		else if (180 <= days && days < 360)
		{
			return 8;
		}
		else 
		{
			return 10;
		}
	}

	private List<Integer> getFavoriteTopicsOfUser(String myUserName) {
		List<Integer> favoriteTopic = new ArrayList<>();
		for(var p : userRepository.getById(myUserName).getInformation().getTopics())
		{
			favoriteTopic.add(p.getTopicID());
		}
		return favoriteTopic;
	}

	private GroupStudying getMostBlogCommentReplyGroup(String myUserName) {
		GroupStudying group = null;
		var maxInteraction = 0;
		for (var p : userRepository.getById(myUserName).getGroups())
		{
			AtomicInteger currentInteraction = new AtomicInteger(0);
			p.getBlogs().stream().forEach(blog -> {
		        if (blog.getUserCreated().equals(myUserName)) currentInteraction.incrementAndGet();
		        blog.getComments().stream().forEach(comment -> {
		        	if (comment.getUserComment().equals(myUserName)) currentInteraction.incrementAndGet();
		        	comment.getReplies().stream().forEach(reply -> {
		        		if (reply.getUserReplied().equals(myUserName)) currentInteraction.incrementAndGet();
		        	});
		        });
		    });
			
			if (currentInteraction.get() > maxInteraction) 
			{
				maxInteraction = currentInteraction.get();	
				group = p;
			}
		}
		return group;
	}

	private GroupStudying getMostMessageGroup(String myUserName) {
		GroupStudying group = null;
		var maxMessages = 0;
		for (var p : userRepository.getById(myUserName).getGroups())
		{
			AtomicInteger currentInteraction = new AtomicInteger(0);
			p.getMessages().stream().forEach(mess -> {
		        if (mess.getUser().equals(myUserName)) currentInteraction.incrementAndGet();
		    });
			
			if (currentInteraction.get() > maxMessages) 
			{
				maxMessages = currentInteraction.get();	
				group = p;
			}
		}
		return group;
	}
}