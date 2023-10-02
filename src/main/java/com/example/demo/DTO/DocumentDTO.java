package com.example.demo.DTO;

import java.util.Date;

import com.example.demo.entities.Document;
import com.example.demo.entities.DocumentType;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class DocumentDTO {

	private int documentID;
	private String Header;
	private byte[] File;
	private Date dateUploaded;
	private DocumentType type;
    private UserDTO user;
    
    public DocumentDTO(Document document)
    {
    	this.documentID = document.getDocumentID();
    	this.Header = document.getHeader();
    	this.File = document.getFile();
    	this.dateUploaded = document.getDateUploaded();
    	this.type = document.getType();
    	if (document.getUser() != null)
    	this.user = new UserDTO(document.getUser());
    }
}
