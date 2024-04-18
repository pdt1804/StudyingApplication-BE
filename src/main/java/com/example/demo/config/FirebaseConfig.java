package com.example.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Random;

import org.springframework.context.annotation.Configuration;

import com.example.demo.BackendApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

	@PostConstruct
	public void FirebaseInit()
	{
		try
		{
			var classLoader = BackendApplication.class.getClassLoader();
			File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
			FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
	
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setStorageBucket("findingfriendapplication.appspot.com")
					  .build();

			FirebaseApp.initializeApp(options);
			
	        
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

