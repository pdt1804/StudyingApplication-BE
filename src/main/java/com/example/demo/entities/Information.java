package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "InfoUser")
@Getter
@Setter
public class Information {

	@Id
	@Column(name = "Information_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int InfoID;
	private String fulName;
	@Lob
	private String description;
	private int PhoneNumber;
	@Lob
    private String Image;
	private String publicID;
	private String Gender;
	private int yearOfBirth;
	@OneToMany(mappedBy = "information")
	@JsonIgnore
	private List<Upside> listUpside;
	@JsonIgnore
	@OneToMany(mappedBy = "information")
	private List<Downside> listDownside;
	
	@ManyToMany(mappedBy = "users")
    private List<Topic> topics = new ArrayList<>();
	
	public Information(int infoID, String fulName, int phoneNumber, String image, String gender, int yearOfBirth,
			List<Upside> listUpside, List<Downside> listDownside) {
		super();
		InfoID = infoID;
		this.fulName = fulName;
		PhoneNumber = phoneNumber;
		Image = image;
		Gender = gender;
		this.yearOfBirth = yearOfBirth;
		this.listUpside = listUpside;
		this.listDownside = listDownside;
	}
	
	public Information()
	{
		
	}

	
}
