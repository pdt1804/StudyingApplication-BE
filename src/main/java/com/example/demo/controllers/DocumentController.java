package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.DocumentDTO;
import com.example.demo.entities.Document;
import com.example.demo.services.DocumentService;

@RequestMapping("api/v1/document")
@RestController
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/getDocumentByID")
	public DocumentDTO getDocumentByID(@RequestParam("groupID") int groupID, @RequestParam("documentID") int documentID)
	{
		return documentService.getDocument(groupID, documentID);
	}
	
	@GetMapping("/getAllDocumentOfGroup")
	public List<Document> getAllDocumentOfGroup(@RequestParam("groupID") int groupID)
	{
		return documentService.getAllDocumentOfGroup(groupID);
	}
	
	@PostMapping("/addDocument")
	public int addDocument(@RequestParam("file") MultipartFile file, @RequestParam("groupID") int groupID, @RequestParam("userName") String userName)
	{
		return documentService.addDocument(file, groupID, userName);
	}
	
	@DeleteMapping("/deleteDocument")
	public int deleteDocument(@RequestParam("groupID") int groupID, @RequestParam("documentID") int documentID)
	{
		return documentService.deleteDocument(groupID, documentID);
	}
}
