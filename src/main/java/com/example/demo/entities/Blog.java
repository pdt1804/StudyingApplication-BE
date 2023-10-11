package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long blogID;
	@Column(columnDefinition = "TEXT")
	private String Content;
	@Lob
	private byte[] images;
	private int likeCount;
	private Date dateCreated;
	@OneToOne
	@JoinColumn(name = "subject_id", nullable = true)
	private Subject subject;
	@ManyToOne
	@JoinColumn(name = "user_blog_ID", nullable = true)
	private User userCreated;
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "group_blog_id", nullable = true)
	private GroupStudying group; 
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	
}
