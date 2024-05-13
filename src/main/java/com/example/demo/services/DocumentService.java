package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.DTO.DocumentDTO;
import com.example.demo.entities.Document;
import com.example.demo.entities.DocumentType;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.DocumentManagement;
import com.google.api.client.googleapis.util.Utils;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@Service
public class DocumentService implements DocumentManagement {

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private NotifycationRepository notifycationRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	 
	@Override
	public Document getDocumentById(int id)
	{
		return documentRepository.getById(id);
	}
	 
	@Override
	public int addDocument(MultipartFile file ,int groupID, String userName)
	{
		String name = file.getOriginalFilename();
		DocumentType type;
		if (name.endsWith(".doc") || name.endsWith(".docx")) type = DocumentType.word;
		else if (name.endsWith(".xls") || name.endsWith(".xlsx")) type = DocumentType.excel;
		else if (name.endsWith(".ppt") || name.endsWith(".pptx")) type = DocumentType.powerpoint;
		else if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) type = DocumentType.image;
		else if (name.endsWith(".mp4") || name.endsWith(".mov")) type = DocumentType.video;
		else if (name.endsWith(".txt")) type = DocumentType.txt;
		else if (name.endsWith(".pdf")) type = DocumentType.pdf;
		else 
		{
			System.out.println("Không định dạng được file.");
			type = DocumentType.unidentify;
		}

		try
		{

			Random rd = new Random();
			String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
			String url = "https://firebasestorage.googleapis.com/v0/b/findingfriendapplication.appspot.com/o/" + nameOnCloud + "?alt=media";
			
			var user = userRepository.getById(userName);
			var group = groupStudyingRepository.getById(groupID);
			var doc = new Document().builder()
					 .user(user).group(group).dateUploaded(new Date()).File(url).type(type).Header(nameOnCloud).build();
			
			Bucket bucket = StorageClient.getInstance().bucket();
			var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
			
			group.getDocuments().add(doc);
			
			int i = documentRepository.save(doc).getDocumentID();
			
			// theem thoong baso
			Notifycation notifycation = new Notifycation().builder()
						 .Header("Tài liệu mới!!!")
						 .Content("Nhóm " + group.getNameGroup() + " có tài liệu mới với mã tài liệu là " + doc.getDocumentID()+ ", click vào đây để xem ")
						 .dateSent(new Date()).notifycationType(NotifycationType.admin)
						 .groupStudying(group).build();
			
			group.getNotifycations().add(notifycation);
			groupStudyingRepository.save(group);
			
			if (notifycation.getUserSeenNotifycation() == null) notifycation.setUserSeenNotifycation(new ArrayList<>());
			if (notifycation.getUsers() == null) notifycation.setUsers(new ArrayList<>());

			for (var p: group.getUsers())
			{
				notifycation.getUserSeenNotifycation().add(p.getUserName());
				notifycation.getUsers().add(p);
				p.getNotifycations().add(notifycation);
			}
			
			notifycationRepository.save(notifycation);						
			userRepository.save(user);
			
			return i;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	 
	@Override
	public List<Document> getAllDocumentOfGroup(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getDocuments().stream().sorted((d1,d2) -> d2.getDateUploaded().compareTo(d1.getDateUploaded())).toList();
	}
	 
	@Override
	public int deleteDocument(int groupID, int documentID)
	{
		try
		{
			var group = groupStudyingRepository.getById(groupID);
			var document = documentRepository.getById(documentID);
			
			Bucket bucket = StorageClient.getInstance().bucket();
			Blob blob = bucket.get(document.getHeader());
	        blob.delete();
			
			group.getDocuments().remove(document);
			groupStudyingRepository.save(group);
			
			document.setUser(null);
			document.setGroup(null);
			
			documentRepository.delete(document);
			
			return documentID;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
