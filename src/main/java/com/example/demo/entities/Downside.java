package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Downside {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int downsideID;
	private String downsideName;
	@ManyToOne
    @JoinColumn(name="Information_ID", nullable=false)
    private Information information;
	
	public Downside(int downsideID, String downsideName, Information information) {
		super();
		this.downsideID = downsideID;
		this.downsideName = downsideName;
		this.information = information;
	}
	
	public Downside()
	{
		
	}
}
