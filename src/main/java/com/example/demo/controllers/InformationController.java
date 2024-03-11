package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Downside;
import com.example.demo.entities.Information;
import com.example.demo.entities.Upside;
import com.example.demo.entities.User;
import com.example.demo.services.InformationService;
import com.example.demo.services.JwtService;
import com.example.demo.services.UpsideAndDownsideService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/information")
public class InformationController {

	@Autowired
	private InformationService informationService;
	
	@Autowired
	private UpsideAndDownsideService upsideAndDownsideService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	public String extractTokenToGetUsername(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
	}
	
	@PostMapping("/updateInformation")
	public void updateInformation(@RequestBody Information information)
	{
		informationService.updateInformation(information);
	}
	
	@GetMapping("/getInformation")
	public Information getInformationbyUsername(HttpServletRequest request)
	{
		return informationService.getInformationbyUsername(extractTokenToGetUsername(request));
	}
	
	@GetMapping("/getAvatar")
	public String getAvatar(HttpServletRequest request)
	{
		return informationService.getInformationbyUsername(extractTokenToGetUsername(request)).getImage();
    }	
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassWord") String newPassWord, @RequestParam("currentPassWord") String currentPassWord, HttpServletRequest request)
	{
		return informationService.changePassword(extractTokenToGetUsername(request), newPassWord, currentPassWord);
	}
	
	@PostMapping("/changeAvatar")
	public String changeAvatar(@RequestParam ("image") String image, HttpServletRequest request)
	{
		return informationService.changeAvatar(image, extractTokenToGetUsername(request));
	}
	
	@PostMapping("/changeAvatarCloud")
	public String changeAvatarCloud(@RequestParam ("image") MultipartFile image, HttpServletRequest request)
	{
		return informationService.changeAvatarCloud(image, extractTokenToGetUsername(request));
	}
	
	@GetMapping("/ExtractBearerToken")
	public String ExtractBearerToken(HttpServletRequest request)
	{
		return extractTokenToGetUsername(request);
		
	}
	
	@GetMapping("/GetUser")
	public User GetUser(@RequestParam("userName") String userName)
	{
		return userService.getUser(userName);
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
