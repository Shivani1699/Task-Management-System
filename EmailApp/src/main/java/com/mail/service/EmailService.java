package com.mail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mail.dto.SendEmailParams;
import com.mail.entity.EmailDto;
import com.mail.entity.FileMaster;
import com.mail.entity.Task;

@Service
public interface EmailService {

	public void sendSaveEmailData(SendEmailParams sendEmailParams) throws Exception;
	public void sendSaveEmailData(EmailDto emailDto) throws Exception;
	public List<EmailDto> getAll(String scheduledSend);
	

	public void uploadFileTrigger(FileMaster fileMaster)throws Exception;
//List<EmailMaster> findByScheduledSendService(String scheduledSend) throws Exception;
	
	
	public void TaskTrigger(Task task)throws Exception;

	public List<EmailDto> getEmailMasterByScheduledSend(String scheduledSend);
//	public List<SendEmailParams> dataTransfer(SendEmailParams sendEmailParams,String scheduledSend) ;
		
	}