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
public class Upside {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int upsideID;
	private String upsideName;
	@ManyToOne
    @JoinColumn(name="Information_ID", nullable=false)
    private Information information;
	public Upside(int upsideID, String upsideName, Information information) {
		super();
		this.upsideID = upsideID;
		this.upsideName = upsideName;
		this.information = information;
	}
	public Upside()
	{
		
	}
}
