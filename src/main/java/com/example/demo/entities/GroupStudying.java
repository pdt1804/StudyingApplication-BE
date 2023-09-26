package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne
	private User leaderOfGroup;
	@ManyToMany
    private List<User> users = new ArrayList<>();
	
	public GroupStudying(int groupID, byte[] imageGroup, String nameGroup, Date dateCreated, Set<User> users) {
		super();
		this.groupID = groupID;
		this.imageGroup = imageGroup;
		this.nameGroup = nameGroup;
		this.dateCreated = dateCreated;
	}
	
	public GroupStudying()
	{
		
	}
}
