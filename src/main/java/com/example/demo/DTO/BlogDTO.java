package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.Subject;
import com.example.demo.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class BlogDTO {

	private long blogID;
	private String Content;
	private List<String> image = new ArrayList<>();
	private int likeCount;
	private Date dateCreated;
	private Subject subject;
	private User userCreated;
	private GroupStudying group; 
	private List<Comment> comments = new ArrayList<>();
	
	public BlogDTO(Blog blog)
	{
		this.blogID = blog.getBlogID();
		this.Content = blog.getContent();
		this.image = blog.getImage();
		this.likeCount = blog.getLikes().size();
		this.dateCreated = blog.getDateCreated();
		this.subject = blog.getSubject();
		this.userCreated = blog.getUserCreated();
		this.group = blog.getGroup();
		this.comments = blog.getComments();
	}
	
}
