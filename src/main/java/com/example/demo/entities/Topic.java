package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int topicID;
	public String topicName;
	@Lob
    private String Image;
	
	@ManyToMany
	@JsonIgnore
	@JoinTable
	(
		name = "information_topic",
	    joinColumns = @JoinColumn(name = "info_id"),
	    inverseJoinColumns = @JoinColumn(name = "topicID")
    )
    private List<Information> users = new ArrayList<>();
	
	public Topic(String name, String image)
	{
		topicName = name;
		this.Image = image;
	}
}
