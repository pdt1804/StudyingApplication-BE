package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.GroupStudying;
import com.example.demo.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private String userName;
    private String passWord;
	private String Email;
	private String fulName;
 
    public UserDTO() {
    	
    }

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.Email = user.getEmail();
        this.fulName = user.getInformation().getFulName();
    }

}