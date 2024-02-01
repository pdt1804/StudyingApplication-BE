package com.example.demo.serviceInterfaces;

import com.example.demo.entities.Information;
import com.example.demo.entities.User;

public interface InformationManagement {

	public Information getInformationbyID(int ID);
	
	public Information getInformationbyUsername(String userName);
	
	public Information createInformation();
	
	public void updateInformation(Information information);
	
	public String changePassword(String userName, String newPassWord, String currentPassWord);
	
	public String changeAvatar(String file, String userName);
}
