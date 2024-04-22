package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;

public interface CommentManagement {

	public void commentBlog(long blogID, String userName, String content, List<String> userNames, List<MultipartFile> files);
	
	public List<Comment> getAllCommentOfBlog(long blogID);
}
