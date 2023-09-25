package com.example.demo.entities;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	@ManyToMany
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_name"), inverseJoinColumns = @JoinColumn(name = "groupid"))
    private Set<GroupStudying> groups = new HashSet<>();
  
	
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
