package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.Reply;

public interface ReplyManagement {

	public List<Reply> getAllReplyOfComment(int commentID);
	
	public void replyComment(int commentID, String userName, String content, List<String> userNames, List<MultipartFile> files);
}
