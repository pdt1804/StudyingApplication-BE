package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Downside;
import com.example.demo.entities.Information;
import com.example.demo.entities.Upside;
import com.example.demo.services.InformationService;
import com.example.demo.services.UpsideAndDownsideService;

@RestController
@RequestMapping("/api/v1/information")
public class InformationController {

	@Autowired
	private InformationService informationService;
	
	@Autowired
	private UpsideAndDownsideService upsideAndDownsideService;
	
	@PostMapping("/updateInformation")
	public void updateInformation(@RequestBody Information information)
	{
		informationService.updateInformation(information);
	}
	
	@GetMapping("/getInformation")
	public Information getInformationbyID(@RequestParam("infoID") int id)
	{
		return informationService.getInformationbyID(id);
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String userName, @RequestParam String newPassWord, @RequestParam String currentPassWord)
	{
		return informationService.changePassword(userName, newPassWord, currentPassWord);
	}
	
	@PostMapping("/changeAvatar")
	public String changeAvatar(@RequestParam ("file") MultipartFile file, @RequestParam ("userName") String userName)
	{
		return informationService.changeAvatar(file, userName);
	}
	
	@GetMapping("/getAllUpside")
	public List<Upside> getAllUpside()
	{
		return upsideAndDownsideService.getAllUpside();
	}
	
	@GetMapping("/getAllDownside")
	public List<Downside> getAllDownside()
	{
		return upsideAndDownsideService.getAllDownside();
	}
}
