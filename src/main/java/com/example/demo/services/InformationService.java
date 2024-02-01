package com.example.demo.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Information;
import com.example.demo.entities.User;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.InformationManagement;

@Service
public class InformationService implements InformationManagement {

	@Autowired
	private InformationRepository informationRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Information getInformationbyID(int ID)
	{
		var listInformation = informationRepository.findAll();
		for (var i : listInformation)	
		{
			if (i.getInfoID() == ID)
			{
				return informationRepository.getById(ID);				
			}
		}
		return null;
	}
	
	@Override
	public Information getInformationbyUsername(String userName)
	{
		for (var p : informationRepository.findAll())
		{
			if (p.getInfoID() == userService.getUser(userName).getInformation().getInfoID())
			{
				return p;
			}
		}
		return null;
	}
	
	@Override
	public Information createInformation()
	{
		return informationRepository.save(new Information());
	}
	
	@Override
	public void updateInformation(Information information)
	{
		var existingInformation = getInformationbyID(information.getInfoID());
		existingInformation.setFulName(information.getFulName());
		existingInformation.setYearOfBirth(information.getYearOfBirth());
		existingInformation.setGender(information.getGender());
		//existingInformation.setListDownside(information.getListDownside());
		//existingInformation.setListUpside(information.getListUpside());
		existingInformation.setPhoneNumber(information.getPhoneNumber());
		//existingInformation.setImage(information.getImage());
		informationRepository.save(existingInformation);
	}
	
	@Override
	public String changePassword(String userName, String newPassWord, String currentPassWord)
	{
		User user = userService.GetUserByUsername(userName);
		
		if (encoder.matches(currentPassWord, user.getPassWord()))
		{
			user.setPassWord(encoder.encode(newPassWord));
		    userService.changePassword(user);
		    return "Successful";
		}
		else
		{
			return "Failed";
		}
		
	}
	
	@Override
	public String changeAvatar(String file, String userName)
	{
		try
		{
			User user = userService.GetUserByUsername(userName);
			if (user == null)
			{
				return "Không tồn tại tài khoản";
			}
			else
			{
				user.getInformation().setImage(file);
				userService.changeAvatar(user);
				System.out.println("thanhf coong");
				return "Thay đổi ảnh thành công";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("that bai");
			return "Thay đổi không thành công";
		}
	}
}
