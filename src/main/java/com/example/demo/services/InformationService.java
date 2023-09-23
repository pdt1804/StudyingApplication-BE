package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Information;
import com.example.demo.entities.User;
import com.example.demo.repositories.InformationRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class InformationService {

	@Autowired
	private InformationRepository informationRepository;
	
	@Autowired
	private UserService userService;
	
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
	
	public Information createInformation()
	{
		return informationRepository.save(new Information());
	}
	
	public void updateInformation(Information information)
	{
		var existingInformation = getInformationbyID(information.getInfoID());
		existingInformation.setFulName(information.getFulName());
		existingInformation.setDateOfBirth(information.getDateOfBirth());
		existingInformation.setGender(information.getGender());
		existingInformation.setListDownside(information.getListDownside());
		existingInformation.setListUpside(information.getListUpside());
		existingInformation.setPhoneNumber(information.getPhoneNumber());
		//existingInformation.setImage(information.getImage());
		informationRepository.save(existingInformation);
	}
	
	public String changePassword(String userName, String newPassWord, String currentPassWord)
	{
		User user = userService.GetUserByUsername(userName);
		if (user.getPassWord().equals(currentPassWord))
		{
			user.setPassWord(newPassWord);
		    userService.changePassword(user);
		    return "Đổi thành công";
		}
		else
		{
			return "Sai mật khẩu";
		}
	}
	
	public String changeAvatar(MultipartFile file, String userName)
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
				user.getInformation().setImage(file.getBytes());
				userService.changeAvatar(user);
				return "Thay đổi ảnh thành công";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "Thay đổi không thành công";
		}
	}
}
