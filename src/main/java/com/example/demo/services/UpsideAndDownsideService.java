package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Downside;
import com.example.demo.entities.Upside;
import com.example.demo.repositories.DownsideRepository;
import com.example.demo.repositories.UpsideRepository;

@Service
public class UpsideAndDownsideService {

	@Autowired
	private UpsideRepository upsideRepository;
	
	@Autowired
	private DownsideRepository downsideRepository;
	
	public List<Upside> getAllUpside()
	{
		return upsideRepository.findAll();
	}
	
	public List<Downside> getAllDownside()
	{
		return downsideRepository.findAll();
	}
	
}
