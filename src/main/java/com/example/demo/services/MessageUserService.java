package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
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
import com.example.demo.entities.MessageUser;
import com.example.demo.entities.MessageUserStatus;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.FriendShipRepository;
import com.example.demo.repositories.MessageUserRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.MessageUserManagement;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class MessageUserService implements MessageUserManagement{

	@Autowired
	private MessageUserRepository messageUserRepository;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private FriendShipRepository friendShipRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Override
	public User getSentUser(long id)
	{
		return messageUserRepository.getById(id).getSentUser();
	}
	
	public void uploadFileToCloudinary(MultipartFile file, long messID) throws IOException
	{
		try
		{
			var mess = messageUserRepository.getById(messID);
			Map<String, String> data = cloudinary.uploader().upload(file.getBytes(), Map.of());
			File f = new File(data.get("url"), data.get("public_id"), mess);
			fileRepository.save(f);
			mess.getFiles().add(f);
			messageUserRepository.save(mess);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public long sendMessage(MessageUser mess, String fromUserName, String toUserName)
	{
		try 
		{
			System.out.println("reach");

			if (toUserName.equals("Chatbot") || fromUserName.equals("Chatbot"))
			{
				var fromUser = userRepository.getById(fromUserName);
				var toUser = userRepository.getById(toUserName);
				
				mess.setStatus(MessageUserStatus.seen);
				mess.setSentUser(fromUser);
				mess.setReceivedUser(toUser);
				mess.setDateSent(new Date());
				
				return messageUserRepository.save(mess).getID();
			}
			else
			{
				var fromUser = userRepository.getById(fromUserName);
				var toUser = userRepository.getById(toUserName);
				
				mess.setStatus(MessageUserStatus.sent);
				mess.setSentUser(fromUser);
				mess.setReceivedUser(toUser);
				mess.setDateSent(new Date());
							
				var friendShip = friendShipRepository.findAll().stream()
						.filter(p -> (p.getUser().getUserName().equals(fromUserName) && p.getFriend().getUserName().equals(toUserName)) 
								|| (p.getUser().getUserName().equals(toUserName) && p.getFriend().getUserName().equals(fromUserName)))
						.findFirst().orElse(null);
				
				if (friendShip != null)
				{
					friendShip.setLastTimeEdited(new Date());
					friendShipRepository.save(friendShip);
					
					var message = messageUserRepository.save(mess);
										
					messagingTemplate.convertAndSend("/private/queue/" + friendShip.getFriendShip_ID(), message);
					return message.getID();
				}
				else
				{
					return -1;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public void UploadImageForMessage(List<MultipartFile> files, String fromUserName, String toUserName) throws java.io.IOException
	{		
		if (files.size() == 0)
		{
			return;
		}
		
		var mess = messageUserRepository.getById(sendMessage(new MessageUser(), fromUserName, toUserName));
		
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
				
				//uploadFileToCloudinary(file, fromUserName, toUserName);;
			}
			
			//messageUserRepository.save(obj);
		}
	}
	
	@Override
	public long saveChatBotMessage(MessageUser mess, String toUserName)
	{
		try 
		{
			var fromUser = userRepository.getById("Chatbot");
			var toUser = userRepository.getById(toUserName);
			
			mess.setStatus(MessageUserStatus.seen);
			mess.setSentUser(fromUser);
			mess.setReceivedUser(toUser);
			mess.setDateSent(new Date());
			
			return messageUserRepository.save(mess).getID();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public List<MessageUser> loadMessageInUser(String fromUserName, String toUserName)
	{
		List<MessageUser> listMessageUser = new ArrayList<>();
		
		for (var mess : messageUserRepository.findAll())
		{
			if (mess.getSentUser().getUserName().equals(fromUserName) && mess.getReceivedUser().getUserName().equals(toUserName))
			{
				listMessageUser.add(mess);
			}
			else if (mess.getSentUser().getUserName().equals(toUserName) && mess.getReceivedUser().getUserName().equals(fromUserName))
			{
				mess.setStatus(MessageUserStatus.seen);
				listMessageUser.add(mess);
				messageUserRepository.save(mess);
			}
			else
			{
				continue;
			}
		}
		
		return listMessageUser.stream().sorted((m1,m2) -> m1.getDateSent().compareTo(m2.getDateSent())).collect(Collectors.toList());
	}
	
	@Override
	public int getFriendID(String fromUserName, String toUserName)
	{
		return friendShipRepository.findAll().stream().filter(p -> (p.getUser().getUserName().equals(fromUserName) && p.getFriend().getUserName().equals(toUserName)) || (p.getUser().getUserName().equals(toUserName) && p.getFriend().getUserName().equals(fromUserName))).findFirst().get().getFriendShip_ID();
	}
	
}
