package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

@Service
public class DocumentService implements DocumentManagement{

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
	public int addDocument(String file ,int groupID, String userName, String name)
	{
		//String url = file.getOriginalFilename();
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
			//Map<String, String> data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			
			//Map<String, String> params = ObjectUtils.asMap("folder", "","resource_type", "auto");
			
			//Map<String, String> data = cloudinary.uploader().upload(file.getBytes(), params);

			var user = userRepository.getById(userName);
			var group = groupStudyingRepository.getById(groupID);
			var doc = new Document().builder()
					 .user(user).group(group).dateUploaded(new Date()).File(file).type(type).Header(name).build();
			
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
				notifycation.getUserSeenNotifycation().add(p);
				notifycation.getUsers().add(p);
				p.getNotifycations().add(notifycation);
			}
			
			notifycationRepository.save(notifycation);						
			//int i = documentRepository.save(doc).getDocumentID();
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
			
			group.getDocuments().remove(document);
			groupStudyingRepository.save(group);
			
			document.setUser(null);
			document.setGroup(null);
			
			cloudinary.api().deleteResources(Arrays.asList(document.getPublicID()), ObjectUtils.asMap("type", "upload", "resource_type", "raw"));
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
