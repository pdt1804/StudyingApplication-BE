package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class File {

	@Id
	private String url;
	private String publicId;
	private int height;
	private int width;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blogID", nullable = true)
	@JsonIgnore
	private Blog blog;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "notificationID", nullable = true)
	@JsonIgnore
	private Notifycation notifycation;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "commentID", nullable = true)
	@JsonIgnore
	private Comment comment;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "replyID", nullable = true)
	@JsonIgnore
	private Reply reply;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "messageGroupID", nullable = true)
	@JsonIgnore
	private MessageGroup messageGroup;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "messageUserID", nullable = true)
	@JsonIgnore
	private MessageUser messageUser;
	
	public File(String url, String publicId, Blog blog)
	{
		this.url = url;
		this.publicId = publicId;
		this.blog = blog;
		
		notifycation = null;
		comment = null;
		reply = null;
		messageGroup = null;
		messageUser = null;
	}
	
	public File(String url, String publicId, Notifycation notifycation)
	{
		this.url = url;
		this.publicId = publicId;
		this.notifycation = notifycation;
		
		blog = null;
		comment = null;
		reply = null;
		messageGroup = null;
		messageUser = null;
	}
	
	public File(String url, String publicId, Comment comment)
	{
		this.url = url;
		this.publicId = publicId;
		this.comment = comment;
		
		blog = null;
		notifycation = null;
		reply = null;
		messageUser = null;
		messageGroup = null;
	}
	
	public File(String url, String publicId, Reply reply)
	{
		this.url = url;
		this.publicId = publicId;
		this.reply = reply;
		
		blog = null;
		notifycation = null;
		comment = null;
		messageGroup = null;
		messageUser = null;
	}
	
	public File(String url, String publicId, MessageGroup mess)
	{
		this.url = url;
		this.publicId = publicId;
		this.messageGroup = mess;
		
		blog = null;
		notifycation = null;
		comment = null;
		reply = null;
		messageUser = null;
	}
	
	public File(String url, String publicId, MessageUser mess)
	{
		this.url = url;
		this.publicId = publicId;
		this.messageUser = mess;
		
		blog = null;
		notifycation = null;
		comment = null;
		reply = null;
		messageGroup = null;
	}
	
}
