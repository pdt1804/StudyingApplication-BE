package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class RecoveryCode {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private int Code;
	private String userName;
	private Date DateCreated;
	
	public RecoveryCode()
	{
		
	}
	
	public RecoveryCode(int code, String userName, Date DateCreated) {
		super();
		Code = code;
		this.userName = userName;
		this.DateCreated = DateCreated;
	}
	
	
}
