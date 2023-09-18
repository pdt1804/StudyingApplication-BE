package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Information;
import com.example.demo.services.InformationService;

@RestController
@RequestMapping("/api/information")
public class InformationController {

	@Autowired
	private InformationService informationService;
	
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
	public String changePassword(@RequestParam int userID, @RequestParam String newPassWord, @RequestParam String currentPassWord)
	{
		return informationService.changePassword(userID, newPassWord, currentPassWord);
	}
}
