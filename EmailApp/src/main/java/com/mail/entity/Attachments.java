package com.mail.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Attachments implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long emailDtoAtachmentId;
	
	private long attachmentId;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = true, insertable = false, updatable = false)
	private EmailDto emailDto;

	public long getEmailDtoAtachmentId() {
		return emailDtoAtachmentId;
	}

	public void setEmailDtoAtachmentId(long emailDtoAtachmentId) {
		this.emailDtoAtachmentId = emailDtoAtachmentId;
	}

	public long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public EmailDto getEmailDto() {
		return emailDto;
	}

	public void setEmailDto(EmailDto emailDto) {
		this.emailDto = emailDto;
	}


}
