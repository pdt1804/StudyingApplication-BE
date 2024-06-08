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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	@Lob
	@Column(columnDefinition = "TEXT")
	private String Content;
	private Date dateSent;
	@OneToMany
	@JoinColumn(name = "status_user_name", nullable = true)
	@JsonIgnore
	private List<User> statusMessageWithUsers = new ArrayList<>();
	@ManyToOne
	private User user;
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = true)
	@JsonIgnore
	private GroupStudying group;
	@OneToMany(mappedBy = "messageGroup", cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<>();
}
