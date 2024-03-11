package com.example.demo.config;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryService {

	@Bean
	public Cloudinary getCloudinary()
	{
		var config = new HashMap<>();
        config.put("cloud_name", "ddsbj1z4x");
        config.put("api_key", "698816719337434");
        config.put("api_secret", "B3H1aL9FI2guvg6X5jGifxUdGoQ");
        config.put("secure", true);
        return new Cloudinary(config);
	}
}
