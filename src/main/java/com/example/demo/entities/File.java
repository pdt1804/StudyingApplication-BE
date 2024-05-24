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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blogID", nullable = true)
	@JsonIgnore
	private Blog blog;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "notificationID", nullable = true)
	@JsonIgnore
	private Notifycation notifycation;
	
	public File(String url, String publicId, Blog blog)
	{
		this.url = url;
		this.publicId = publicId;
		this.blog = blog;
		
		notifycation = null;
	}
	
	public File(String url, String publicId, Notifycation notifycation)
	{
		this.url = url;
		this.publicId = publicId;
		this.notifycation = notifycation;
		
		blog = null;
	}
	
}
