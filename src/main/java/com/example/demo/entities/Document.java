package com.example.demo.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int documentID;
	private String Header;
	@Lob
	private byte[] File;
	private Date dateUploaded;
	private DocumentType type;
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = true)
	@JsonIgnore
	private GroupStudying group;
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "user_name", nullable = true)
    private User user;

}
