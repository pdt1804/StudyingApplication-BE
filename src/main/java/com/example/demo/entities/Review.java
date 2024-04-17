package com.example.demo.entities;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reviewID;
	private Date dateCreated;
	private Date dateEdited;
	@Lob
	private String content;
	private double rating;
	@OneToOne
	@JoinColumn(name = "userName")
	private User reviewer;
	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "groupID")
	private GroupStudying group;
	
}
