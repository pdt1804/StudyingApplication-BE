package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	private int PhoneNumber;
	@Lob
    private byte[] Image;
	private String Gender;
	private Date dateOfBirth;
	@OneToMany(mappedBy = "information")
	private List<Upside> listUpside;
	@OneToMany(mappedBy = "information")
	private List<Downside> listDownside;
	
	public Information(int infoID, String fulName, int phoneNumber, byte[] image, String gender, Date dateOfBirth,
			List<Upside> listUpside, List<Downside> listDownside) {
		super();
		InfoID = infoID;
		this.fulName = fulName;
		PhoneNumber = phoneNumber;
		Image = image;
		Gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.listUpside = listUpside;
		this.listDownside = listDownside;
	}
	
	public Information()
	{
		
	}
	
}
