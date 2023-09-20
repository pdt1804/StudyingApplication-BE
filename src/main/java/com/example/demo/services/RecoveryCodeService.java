package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entities.RecoveryCode;
import com.example.demo.repositories.RecoveryCodeRepository;

@Service
public class RecoveryCodeService {
	
	@Autowired
	private RecoveryCodeRepository recoveryCodeRepository;
	
	public void CreateCode(int Code, String userName)
	{
		recoveryCodeRepository.save(new RecoveryCode(Code, userName, new Date()));
	}
	
	public Boolean CheckValidate(RecoveryCode recoveryCode)
	{
		Date currentTime = new Date();
	    Date timeCreated = recoveryCode.getDateCreated();
	    long timeDifferenceInMillis = currentTime.getTime() - timeCreated.getTime();
	    long oneMinuteInMillis = 60 * 1000;
	    boolean isExpired = timeDifferenceInMillis > oneMinuteInMillis;
	    return !isExpired;
	}
	
	//@Scheduled(fixedRate = 61000)
	private void EliminateInvalidCode()
	{
		List<RecoveryCode> listRecoveryCode = recoveryCodeRepository.findAll();
		for (RecoveryCode p : listRecoveryCode)
		{
			if (CheckValidate(p) == false)
			{
				recoveryCodeRepository.delete(p);
			}
		}
	}
}
