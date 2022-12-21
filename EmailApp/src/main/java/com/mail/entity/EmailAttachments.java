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

import org.springframework.lang.Nullable;

@Entity
public class EmailAttachments  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long emailAttachmentId;
    @Nullable
	private long attachment;
   
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = true, insertable = false, updatable = false)
	private EmailMaster emailMaster;

	public long getEmailAttachmentId() {
		return emailAttachmentId;
	}

	public void setEmailAttachmentId(long emailAttachmentId) {
		this.emailAttachmentId = emailAttachmentId;
	}

	public long getAttachment() {
		return attachment;
	}

	public void setAttachment(long attachment) {
		this.attachment = attachment;
	}

	public EmailMaster getEmailMaster() {
		return emailMaster;
	}

	public void setEmailMaster(EmailMaster emailMaster) {
		this.emailMaster = emailMaster;
	}

}
