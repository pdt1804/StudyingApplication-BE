package com.example.demo.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
	private byte[] imageGroup;
	private String nameGroup;
	private Date dateCreated;
	@ManyToMany
    private Set<User> users = new HashSet<>();
	
	public GroupStudying(int groupID, byte[] imageGroup, String nameGroup, Date dateCreated, Set<User> users) {
		super();
		this.groupID = groupID;
		this.imageGroup = imageGroup;
		this.nameGroup = nameGroup;
		this.dateCreated = dateCreated;
		this.users = users;
	}
	
	public GroupStudying()
	{
		
	}
}
