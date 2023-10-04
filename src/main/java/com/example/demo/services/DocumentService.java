package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.DocumentDTO;
import com.example.demo.entities.Document;
import com.example.demo.entities.DocumentType;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	 
	public int addDocument(MultipartFile file ,int groupID, String userName)
	{
		String url = file.getOriginalFilename();
		DocumentType type;
		if (url.endsWith(".doc") || url.endsWith(".docx")) type = DocumentType.word;
		else if (url.endsWith(".xls") || url.endsWith(".xlsx")) type = DocumentType.excel;
		else if (url.endsWith(".ppt") || url.endsWith(".pptx")) type = DocumentType.powerpoint;
		else if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg")) type = DocumentType.image;
		else if (url.endsWith(".mp4")) type = DocumentType.video;
		else if (url.endsWith(".pdf")) type = DocumentType.pdf;
		else 
		{
			System.out.println("Không định dạng được file.");
			type = DocumentType.unidentify;
		}

		try
		{
			var doc = documentRepository.save(new Document().builder()
					 .user(userRepository.getById(userName))
					 .group(groupStudyingRepository.getById(groupID))
					 .dateUploaded(new Date())
					 .File(file.getBytes())
					 .type(type)
					 .Header(file.getName())
					 .build());
			var group = groupStudyingRepository.getById(groupID);
			group.getDocuments().add(doc);
			group.setLastTimeEdited(new Date());
			
			int i = documentRepository.save(doc).getDocumentID();
			groupStudyingRepository.save(group);
			return i;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	public DocumentDTO getDocument(int groupID, int documentID)
	{
		return new DocumentDTO(groupStudyingRepository.getById(groupID).getDocuments()
									.stream().filter(p -> p.getDocumentID() == documentID).findFirst().orElse(null));
	}
	
	public List<Document> getAllDocumentOfGroup(int groupID)
	{
		return groupStudyingRepository.getById(groupID).getDocuments();
	}
	
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
