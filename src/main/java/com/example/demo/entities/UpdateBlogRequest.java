package com.example.demo.entities;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateBlogRequest {
	private TypeRequest type;
	private MultipartFile file;
	private String fileName;
}
