package com.mail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mail.constants.AppConstant.Message;
import com.mail.constants.AppConstant.Resource;
import com.mail.dto.SendEmailParams;
import com.mail.entity.EmailDto;
import com.mail.entity.FileMaster;
import com.mail.entity.Task;
import com.mail.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailService;

	// This controller method is used to send the Mail with or without attachments.
	@PostMapping(Resource.EMAIL_API)
	public String emailSend(@RequestBody SendEmailParams sendEmailParams) throws Exception {
		emailService.sendSaveEmailData(sendEmailParams);

		return Message.MAIL_SENT_SUCCESFULLY;

	}

	

	@GetMapping("/getByScheduledSend") // @RequestParam
	public List<EmailDto> getEmailByScheduled(@RequestParam String scheduledSend) throws Exception {
		List<EmailDto> listEmailDto = emailService.getEmailMasterByScheduledSend(scheduledSend);
		return listEmailDto;
	}

	@GetMapping("/getAll") // @RequestParam
	public List<EmailDto> getEmailBySchedule(@RequestParam String scheduledSend) throws Exception {
		List<EmailDto> listEmailDto = emailService.getAll(scheduledSend);
		return listEmailDto;
	}
	
	@PostMapping("/uploadMailTrigger")
	public String FileUploadMailTrigger(@RequestBody FileMaster fileMaster) {
		try {
			emailService.uploadFileTrigger(fileMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Mail Triggered For File Upload";
	}
	
	
	@PostMapping("/taskTrigger")
	public String TaskCreateTrigger(@RequestBody Task task) {
		try {
			emailService.TaskTrigger(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Mail Triggered For New Task Creation";
	}
	
}
