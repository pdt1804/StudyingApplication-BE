package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_studying")
@Getter
@Setter
public class GroupStudying {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int groupID;
	@Lob
	private String imageGroup;
	private String publicID;
	private String nameGroup;
	private String passWord;
	private Date dateCreated;
	private Date lastTimeEdited;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_name", nullable = true)
	private User leaderOfGroup; 
	@JsonIgnore
	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
	@OneToMany(mappedBy = "groupStudying")
	@JsonIgnore
	private List<Notifycation> notifycations = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "group")
	private List<Document> documents = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "group")
	private List<MessageGroup> messages = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	@JsonIgnore
	private List<Blog> blogs = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	@JsonIgnore
	private List<Subject> subjects = new ArrayList<>();
	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<Topic> topics = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();
	private List<String> userNameReviewed = new ArrayList<>();
	
	public GroupStudying(int groupID, String imageGroup, String nameGroup, Date dateCreated, Set<User> users, String passWord) {
		super();
		this.passWord = passWord;
		this.groupID = groupID;
		this.imageGroup = imageGroup;
		this.nameGroup = nameGroup;
		this.dateCreated = dateCreated;
	}
	
	public GroupStudying()
	{
		
	}
}
