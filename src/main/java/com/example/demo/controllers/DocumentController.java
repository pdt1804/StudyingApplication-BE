package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.DTO.DocumentDTO;
import com.example.demo.entities.Document;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.services.DocumentService;
import com.example.demo.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("api/v1/document")
@RestController
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private JwtService jwtService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@GetMapping("/getAllDocumentOfGroup")
	public List<Document> getAllDocumentOfGroup(@RequestParam("groupID") int groupID)
	{
		return documentService.getAllDocumentOfGroup(groupID);
	}
	
	@GetMapping("/getDocumentById")
	public DocumentDTO getDocumentById(@RequestParam("documentID") int documentID)
	{
		return new DocumentDTO(documentService.getDocumentById(documentID));
	}
	
	
	@PostMapping("/addDocument")
	public int addDocument(@RequestParam("file") String file, @RequestParam("groupID") int groupID, HttpServletRequest request, @RequestParam("fileName") String fileName)
	{
		try
		{
			return documentService.addDocument(file, groupID, extractTokenToGetUsername(request), fileName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	@DeleteMapping("/deleteDocument")
	public int deleteDocument(@RequestParam("groupID") int groupID, @RequestParam("documentID") int documentID)
	{
		return documentService.deleteDocument(groupID, documentID);
	}
}
