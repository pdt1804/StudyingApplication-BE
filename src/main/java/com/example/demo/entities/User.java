package com.example.demo.entities;


import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UID;
    @Column(unique = true)
    private String userName;
    private String passWord;
	private String Email;
	
	@OneToOne
	@JoinColumn(name = "Users-Infomation")
	private Information information;
	
    public User(int uID, String userName, String passWord, String Email) {
		super();
		UID = uID;
		this.userName = userName;
		this.passWord = passWord;
		this.Email = Email;
	}
    
    public User()
    {
    	
    }
    
}
