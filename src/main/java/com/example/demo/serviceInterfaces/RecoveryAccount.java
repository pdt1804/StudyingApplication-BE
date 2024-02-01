package com.example.demo.serviceInterfaces;

public interface RecoveryAccount {
	
	public int sendOTP(String userName);
	
	public int sendOTPtoEmail(String email);

}
