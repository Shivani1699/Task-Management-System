package com.mail.serviceImpl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mail.dto.SendEmailParams;
import com.mail.entity.Attachments;
import com.mail.entity.EmailAttachments;
import com.mail.entity.EmailDetails;
import com.mail.entity.EmailDto;
import com.mail.entity.EmailMaster;
import com.mail.entity.FileMaster;
import com.mail.entity.Task;
import com.mail.repository.AttachmentsRepository;
import com.mail.repository.EmailAttachmentsRepository;
import com.mail.repository.EmailDetailsRepository;
import com.mail.repository.EmailDtoRepository;
import com.mail.repository.EmailMasterRepository;
import com.mail.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailMasterRepository emailMasterRepository;

	@Autowired
	EmailDetailsRepository emailDetailsRepository;

	@Autowired
	EmailAttachmentsRepository emailAttachmentsRepository;

	@Autowired
	EmailDtoRepository emailDtoRepository;

	@Autowired
	AttachmentsRepository attachmentRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private RestTemplate restTemplate;

	Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	SendEmailParams sendEmailParams;

	@Override
	public void sendSaveEmailData(SendEmailParams sendEmailParams) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		if (sendEmailParams.getAttachment() == null) {

			if (sendEmailParams.getScheduledSend() != null) {
				saverepoEmailDtoWithoutAttachment(sendEmailParams);
			} else {

				try {
					getMimeMessageHelperObject(sendEmailParams, mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				javaMailSender.send(mimeMessage);
				saveRepository(sendEmailParams);
				System.out.println("Mail Send Succesfully...");

			}

		} else {
			if (sendEmailParams.getScheduledSend() != null) {
				saverepoEmailDto(sendEmailParams);
			} else {
				

				String[] string_list = new String[sendEmailParams.getAttachment().length];

				for(int i = 0; i < sendEmailParams.attachment.length; i++){
				    string_list[i] = String.valueOf(sendEmailParams.getAttachment()[i]);
				}
				//List<String> attachment = Arrays.asList(sendEmailParams.getAttachment());
				List<String> attachment ;
				//for(int i=0;i<string_list.length;i++) {
				//	attachment.add(string_list(i));
					attachment=Arrays.asList(string_list);
				//}
				List<String> attachmentPaths = getPathFromAttachment(attachment);

				try {
					getMimeMessageHelperObject(sendEmailParams, mimeMessage, attachmentPaths);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

				javaMailSender.send(mimeMessage);
				saveRepositoryWithAttachments(sendEmailParams);

				System.out.println("Mail Send Succesfully...");
			}
		}
	}

	private MimeMessageHelper getMimeMessageHelperObject(SendEmailParams sendEmailParams, MimeMessage mimeMessage,
			List<String> attachmentPaths) throws MessagingException, ParseException {
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
		mimeMessageHelper.setTo(sendEmailParams.getToAddress());
		mimeMessageHelper.setText(sendEmailParams.getBody());
		mimeMessageHelper.setSubject(sendEmailParams.getSubject());
		mimeMessageHelper.setText(sendEmailParams.getSentType());
		FileSystemResource fileSystem;

		for (String location : attachmentPaths) {
			fileSystem = new FileSystemResource(new File(location));
			mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

		}

		return mimeMessageHelper;
	}

	private MimeMessageHelper getMimeMessageHelperObject(SendEmailParams sendEmailParams, MimeMessage mimeMessage)
			throws MessagingException, Exception {
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
		mimeMessageHelper.setTo(sendEmailParams.getToAddress());
		mimeMessageHelper.setText(sendEmailParams.getBody());
		mimeMessageHelper.setSubject(sendEmailParams.getSubject());
		mimeMessageHelper.setText(sendEmailParams.getSentType());

		return mimeMessageHelper;
	}

	private EmailMaster saveEmailMaster(SendEmailParams sendEmailParams) {
		EmailMaster emailMaster = new EmailMaster();
		emailMaster.setBody(sendEmailParams.getBody());
		Date date = new Date();
		emailMaster.setSendAt(date);
		emailMaster.setSendType(sendEmailParams.getSentType());
		emailMaster.setSubject(sendEmailParams.getSubject());
		// emailMaster.setCreatedAt(date);
		emailMaster.setScheduledSend(sendEmailParams.getScheduledSend());
		return emailMaster;
	}

	private List<EmailDetails> saveEmailDetails(SendEmailParams sendEmailParams) {
		int size = sendEmailParams.getToAddress().length;

		List<EmailDetails> emailDetailsList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setToAddress(sendEmailParams.getToAddress()[i]);
			Date date = new Date();
			// emailDetails.setCreatedAt(date);
			emailDetailsList.add(emailDetails);
		}
		return emailDetailsList;
	}

	private List<EmailAttachments> saveEmailAttachments(SendEmailParams sendEmailParams) {

		List<EmailAttachments> emailAttachmentsList = new ArrayList<>();

		for (int i = 0; i < sendEmailParams.getAttachment().length; i++) {

			EmailAttachments emailAttachments = new EmailAttachments();
			emailAttachments.setAttachment(sendEmailParams.getAttachment()[i]);
			Date date = new Date();
			// emailAttachments.setCreatedAt(date);
			emailAttachmentsList.add(emailAttachments);
		}
		return emailAttachmentsList;
	}

	private void saveRepositoryWithAttachments(SendEmailParams sendEmailParams) {//check----------
		EmailMaster emailMaster = saveEmailMaster(sendEmailParams);

		List<EmailDetails> emailDetailsList = saveEmailDetails(sendEmailParams);
		emailMaster.setEmailDetails(emailDetailsList);

		List<EmailAttachments> emailAttachmentsList = saveEmailAttachments(sendEmailParams);
		emailMaster.setEmailAttachments(emailAttachmentsList);

		emailMasterRepository.save(emailMaster);
	}
	

	private void saveRepository(SendEmailParams sendEmailParams) {
		EmailMaster emailMaster = saveEmailMaster(sendEmailParams);
		List<EmailDetails> emailDetailsList = saveEmailDetails(sendEmailParams);
		emailMaster.setEmailDetails(emailDetailsList);
		emailMasterRepository.save(emailMaster);
	}

	private ResponseEntity<FileMaster> getFileServiceData(String attachmentId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-HEADER_NAME", "XYZ");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<FileMaster> responseEntity = restTemplate.exchange("/file/" + attachmentId, HttpMethod.GET,
				entity, FileMaster.class);

		return responseEntity;
	}

	private List<String> getPathFromAttachment(List<String> attachment) {
		List<String> paths = new ArrayList<>();
		for (String attachmentId : attachment) {
			ResponseEntity<FileMaster> fileResponseEntity = getFileServiceData(attachmentId);
			paths.add(fileResponseEntity.getBody().getPath());
		}
		return paths;
	}

	@Override
	public List<EmailDto> getEmailMasterByScheduledSend(String scheduledSend) {

		List<EmailDto> dtoList = emailDtoRepository.findByScheduledSend(scheduledSend);
		for (int i = 0; i < dtoList.size(); i++) {
			EmailDto emailDto = dtoList.get(i);
			try {
				sendSaveEmailData(emailDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dtoList;

	}

	//@Scheduled(fixedRate = 1000)
	public List<EmailDto> getAll(String scheduledSend) {
		List<EmailDto> allList = emailDtoRepository.findByScheduledSend(scheduledSend);
		
		for(int i=0;i<allList.size();i++) {
			System.out.println(allList.get(i));
		}
		/*
		 * System.out.println(allList.toString()); Date date = new Date(); DateFormat
		 * date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String date_string
		 * = date_format.format(date); for (int i = 0; i < allList.size(); i++) { if
		 * ((allList.get(i).getScheduledSend()).equals(date_string)) { EmailDto emailDto
		 * = allList.get(i); try { sendSaveEmailData(emailDto);
		 * System.out.println("Email has been Sent"); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } }
		 */
	return allList;
	}

	public void saverepoEmailDto(SendEmailParams sendEmailParams) {

		EmailDto emailDto = saveEmailDto(sendEmailParams);

		
		
		List<Attachments> attachmentList = saveEmailDtoAttachments(sendEmailParams);
		//emailDto.setAttachment(attachmentList);
        emailDto.setAttachment(attachmentList);
		
		emailDtoRepository.save(emailDto);
			}
	
	public void saverepoEmailDtoWithoutAttachment(SendEmailParams sendEmailParams) {

		EmailDto emailDto = saveEmailDto(sendEmailParams);

		
		
		/*
		 * List<Attachments> attachmentList = saveEmailDtoAttachments(sendEmailParams);
		 * //emailDto.setAttachment(attachmentList);
		 * emailDto.setAttachment(attachmentList);
		 */
		emailDtoRepository.save(emailDto);
			}

	private EmailDto saveEmailDto(SendEmailParams sendEmailParams) {
		EmailDto emailDto = new EmailDto();
		// emailDto.setEmailMasterId(sendEmailParams.get);
		emailDto.setBody(sendEmailParams.getBody());
		emailDto.setToAddress(sendEmailParams.getToAddress());
		emailDto.setSubject(sendEmailParams.getSubject());
		emailDto.setSentType(sendEmailParams.getSentType());
		// emailDto.setCreatedAt(sendEmailParams.getCreatedAt());
	//	String s=String.valueOf(sendEmailParams.getAttachment());
			Date date = new Date();
		emailDto.setSendAt(date);
		emailDto.setScheduledSend(sendEmailParams.getScheduledSend());
		return emailDto;
	}
	
	private List<Attachments> saveEmailDtoAttachments(SendEmailParams sendEmailParams) {

		List<Attachments> emailDtoAttachmentsList = new ArrayList<>();

		for (int i = 0; i < sendEmailParams.getAttachment().length; i++) {

			Attachments emailAttachments = new Attachments();
			emailAttachments.setAttachmentId(sendEmailParams.getAttachment()[i]);
			Date date = new Date();
			// emailAttachments.setCreatedAt(date);
			emailDtoAttachmentsList.add(emailAttachments);
		}
		return emailDtoAttachmentsList;
	}
	
	
	//------------------------------------------------------------------------------------------------//

	public void sendSaveEmailData(EmailDto emailDto) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		if (emailDto.getAttachment() == null) {

			try {
				getMimeMessageHelperObject(emailDto, mimeMessage);
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			javaMailSender.send(mimeMessage);
			// saveRepository(sendEmailParams);
			System.out.println("Mail Send Succesfully with SpecifiedTIme...");

		} else {

			String[] string_list = new String[emailDto.getAttachment().size()];

			for(int i = 0; i < emailDto.getAttachment().size(); i++){
			    string_list[i] = String.valueOf(emailDto.getAttachment().get(i).getAttachmentId());
			}
			//List<String> attachment = Arrays.asList(sendEmailParams.getAttachment());
			List<String> attachment ;
			//for(int i=0;i<string_list.length;i++) {
			//	attachment.add(string_list(i));
				attachment=Arrays.asList(string_list);
			//}
			List<String> attachmentPaths = getPathFromAttachment(attachment);

			try {
				getMimeMessageHelperObject(emailDto, mimeMessage, attachmentPaths);
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			javaMailSender.send(mimeMessage);
			// saveRepositoryWithAttachments(sendEmailParams);

			System.out.println("Mail Send Succesfully with SpecifiedTIme...");
		}

	}

	private MimeMessageHelper getMimeMessageHelperObject(EmailDto emailDto, MimeMessage mimeMessage)
			throws MessagingException, Exception {

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
		mimeMessageHelper.setTo(emailDto.getToAddress());
		mimeMessageHelper.setText(emailDto.getBody());
		mimeMessageHelper.setSubject(emailDto.getSubject());
		mimeMessageHelper.setText(emailDto.getSentType());

		return mimeMessageHelper;
	}

	private MimeMessageHelper getMimeMessageHelperObject(EmailDto emailDto, MimeMessage mimeMessage,
			List<String> attachmentPaths) throws MessagingException, ParseException {
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
		mimeMessageHelper.setTo(emailDto.getToAddress());
		mimeMessageHelper.setText(emailDto.getBody());
		mimeMessageHelper.setSubject(emailDto.getSubject());
		mimeMessageHelper.setText(emailDto.getSentType());
		FileSystemResource fileSystem;

		for (String location : attachmentPaths) {
			fileSystem = new FileSystemResource(new File(location));
			mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

		}

		return mimeMessageHelper;
	}

	
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------//
	@Override
	public void uploadFileTrigger(FileMaster fileMaster) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			getMimeMessageHelperObject(fileMaster, mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(mimeMessage);
		
	}
	private MimeMessageHelper getMimeMessageHelperObject(FileMaster fileMaster, MimeMessage mimeMessage)
			throws MessagingException, Exception {
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
//		mimeMessageHelper.setFrom(fileMaster.getCreated_by());
		mimeMessageHelper.setTo(fileMaster.getCreated_by());
 		mimeMessageHelper.setText("The file"+fileMaster.getFileName() +" and FileId "+fileMaster.getId()+"Uploaded to TaskID "+fileMaster.getTaskId());
		mimeMessageHelper.setSubject("File Uploaded By "+fileMaster.getCreated_by());
		
		return mimeMessageHelper;
	}
	
	
//-------------------------------------------------------------------------------------------//
	@Override
	public void TaskTrigger(Task task) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			getMimeMessageHelperObject(task, mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(mimeMessage);
		
	}
	
	private MimeMessageHelper getMimeMessageHelperObject(Task task, MimeMessage mimeMessage)
			throws MessagingException, Exception {
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("bharathkgowda1999@gmail.com");
		mimeMessageHelper.setTo(task.getAsignee());
		mimeMessageHelper.setText("The Task"+task.getTaskTitle() +" and TaskId "+task.getTaskId()+" created by "+task.getCreatedBy());
		mimeMessageHelper.setSubject("New Task Created "+task.getCreatedBy());
		
		return mimeMessageHelper;
	}
	
}