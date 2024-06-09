package com.example.demo.entities;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.demo.entities.Notifycation;


import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {

	@Id
    private String userName;
    private String passWord;
	private String Email;
	
	@OneToOne
	@JoinColumn(name = "Users-Infomation")
	private Information information;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "users_names"), inverseJoinColumns = @JoinColumn(name = "group_study_id"))
    private List<GroupStudying> groups = new ArrayList<>();
	
	@ManyToMany
	@JsonIgnore
	@JoinTable
	(
		name = "user_notifycation",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "notifycation_id")
    )
    private List<Notifycation> notifycations = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reviewer")
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();
	
//	@OneToMany
//    @JoinColumn(name="username_notifycation", nullable = true)
//    private List<User> userSeenNotifycation = new ArrayList<>();
	
//	@ManyToMany(mappedBy = "users")
//    private List<Topic> topics = new ArrayList<>();
  
	
    public User(String userName, String passWord, String Email) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.Email = Email;
	}
    
    public User()
    {
    	
    }
    
}
