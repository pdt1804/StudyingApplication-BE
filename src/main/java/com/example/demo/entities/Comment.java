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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commentID;
	@Column(columnDefinition = "TEXT")
	private String Content;
	private Date dateComment;
	@ManyToOne
	@JoinColumn(name = "user_comment_ID", nullable = true)
	private User userComment;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_comment_ID", nullable = true)
	@JsonIgnore
	private Blog blog;
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	private List<Reply> replies = new ArrayList<>();
}
