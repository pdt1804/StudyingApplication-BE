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
import com.example.demo.entities.File;
import com.example.demo.entities.MessageGroup;
import com.example.demo.entities.MessageUser;
import com.example.demo.entities.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.MessageGroupRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.MessageGroupManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import lombok.Synchronized;

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
	
	@Autowired
	private FileRepository fileRepository;
	
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
	
	public void uploadImagesForMessage(int groupID, List<MultipartFile> files, String userName) throws java.io.IOException
	{		
		if (files.size() == 0) return;
		
		var mess = messageGroupRepository.getById(sendMessage(new MessageGroup(), groupID, userName));
		
		if (mess != null)
		{
			for (var file : files)
			{
//				Random rd = new Random();
//				String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
//				Bucket bucket = StorageClient.getInstance().bucket();
//				var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
//				
//				obj.getImages().add(nameOnCloud);
				
				//uploadOneFileToCloudinary(groupID, file, userName);
			}
			
			//messageGroupRepository.save(obj);
		}
	}
	
	@Synchronized
	public void uploadOneFileToCloudinary(long messID, MultipartFile file, String userName, int width, int height) throws IOException
	{
		try
		{
			System.out.println("start");
			var mess = messageGroupRepository.getById(messID);
			Map<String, String> data = cloudinary.uploader().upload(file.getBytes(), Map.of());
			File f = new File(data.get("url"), data.get("public_id"), mess);
			f.setHeight(height);
			f.setWidth(width);
			fileRepository.save(f);		
			mess.getFiles().add(f);
			messageGroupRepository.save(mess);
			System.out.println("finish");
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
		
		var listMessage = group.getMessages().stream().sorted((d1,d2) -> d2.getDateSent().compareTo(d1.getDateSent())).collect(Collectors.toList());
		return listMessage.size() != 0 ? listMessage : null;
	}
	
	@Override
	public User getSentUserInGroup(long id)
	{
		return messageGroupRepository.getById(id).getUser();
	}
	
}