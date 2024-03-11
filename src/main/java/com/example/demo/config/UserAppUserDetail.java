package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAppUserDetail implements UserDetails{

	private User existingUser;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return null;
	}

	@Override
	public String getPassword() 
	{
		return existingUser.getPassWord();
	}

	@Override
	public String getUsername() 
	{
		return existingUser.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return true;
	}

}
