package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.api.ApiResponse;
import com.example.demo.entities.Document;
import com.example.demo.entities.DocumentType;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;

public interface DocumentManagement {

	public Document getDocumentById(int id);
	
	public int addDocument(MultipartFile file ,int groupID, String userName, String fileName);
	
	public List<Document> getAllDocumentOfGroup(int groupID);
	
	public int deleteDocument(int groupID, int documentID);
}
