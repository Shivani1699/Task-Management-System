package com.mail.dto;

import java.util.Arrays;

//import send.mail.entity.FileService;

public class SendEmailParams {

	private String body;

	private String[] toAddress;
	private String subject;
	private String sentType;
	//private Date createdAt;
	public long[] attachment;
	//private long sendAt;
	private String scheduledSend;

	public String getBody() {
		return body;
	}
	

	public long[] getAttachment() {
		return attachment;
	}


	public void setAttachment(long[] attachment) {
		this.attachment = attachment;
	}


	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSentType() {
		return sentType;
	}

	public void setSentType(String sentType) {
		this.sentType = sentType;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	/*
	 * public long getSendAt(Date date) { return sendAt; }
	 * 
	 * public void setSendAt(long sendAt) { this.sendAt = sendAt; }
	 */


	/*
	 * public Date getCreatedAt() { return createdAt; }
	 * 
	 * public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
	 */

	

	

	public String getScheduledSend() {
		return scheduledSend;
	}

	public void setScheduledSend(String scheduledSend) {
		
		this.scheduledSend = scheduledSend;
	}

	public SendEmailParams(String body, String[] toAddress, String subject, String sentType,
			long[] attachment,  String scheduledSend) {
		super();
		this.body = body;
		this.toAddress = toAddress;
		this.subject = subject;
		this.sentType = sentType;
		//this.createdAt = createdAt;
		this.attachment = attachment;
		//this.sendAt = sendAt;
		this.scheduledSend = scheduledSend;
	}

	public SendEmailParams() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "SendEmailParams [body=" + body + ", toAddress=" + Arrays.toString(toAddress) + ", subject=" + subject
				+ ", sentType=" + sentType + ", attachment=" + Arrays.toString(attachment) + ", scheduledSend="
				+ scheduledSend + "]";
	}

	

	

	/*
	 * public void setAttachment(List<EmailAttachments> emailAttachments) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

}
