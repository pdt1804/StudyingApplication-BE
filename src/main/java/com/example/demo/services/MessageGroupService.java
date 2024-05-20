package com.example.demo.services;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.MessageUser;
import com.example.demo.entities.User;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.MessageGroupManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class MessageGroupService implements MessageGroupManagement {

	@Autowired
	private MessageGroupRepository messageGroupRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
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
	
	public void UploadImageForMessage(long id, List<MultipartFile> files) throws java.io.IOException
	{
		MessageGroup obj = messageGroupRepository.getById(id);;
		
		if (files.size() == 0) return;
		
		if (obj != null)
		{
			for (var file : files)
			{
//				Random rd = new Random();
//				String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
//				Bucket bucket = StorageClient.getInstance().bucket();
//				var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
//				
//				obj.getImages().add(nameOnCloud);
				
				uploadFileToCloudinary(file, id);
			}
			
			//messageGroupRepository.save(obj);
		}
	}
	
	public void uploadFileToCloudinary(MultipartFile file, long messID) throws IOException
	{
		try
		{
			var mess = messageGroupRepository.getById(messID);
			Map<String, String> data = cloudinary.uploader().upload(file.getBytes(), Map.of());
			mess.getImages().add(data.get("url") + "-" + data.get("public_id"));
			messageGroupRepository.save(mess);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public List<MessageGroup> loadMessage(String myUserName, int groupID)
	{
		var group = groupStudyingRepository.getById(groupID);
		
		var lastMess = group.getMessages().stream().max((m1,m2) -> m1.getDateSent().compareTo(m2.getDateSent()));
		lastMess.get().getStatusMessageWithUsers().remove(userRepository.getById(myUserName));
		messageGroupRepository.save(lastMess.get());
		
		var listMessage = group.getMessages().stream().sorted((d1,d2) -> d1.getDateSent().compareTo(d2.getDateSent())).collect(Collectors.toList());
		return listMessage.size() != 0 ? listMessage : null;
	}
	
	@Override
	public User getSentUserInGroup(long id)
	{
		return messageGroupRepository.getById(id).getUser();
	}
	
}