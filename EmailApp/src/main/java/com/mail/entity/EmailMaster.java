package com.mail.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class EmailMaster implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long emailMasterId;
	@NotNull
	private String subject;
	@NotNull
	private String body;
	private Date sendAt;
	private String sendType;
	private String scheduledSend;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = false)
	private List<EmailDetails> emailDetails;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = false)
	private List<EmailAttachments> emailAttachments;

	public Long getEmailMasterId() {
		return emailMasterId;
	}

	public void setEmailMasterId(Long emailMasterId) {
		this.emailMasterId = emailMasterId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public List<EmailAttachments> getEmailAttachments() {
		return emailAttachments;
	}

	public void setEmailAttachments(List<EmailAttachments> emailAttachments) {
		this.emailAttachments = emailAttachments;
	}

	public List<EmailDetails> getEmailDetails() {
		return emailDetails;
	}

	public void setEmailDetails(List<EmailDetails> emailDetails) {
		this.emailDetails = emailDetails;
	}

	
	  public String getScheduledSend() { return scheduledSend; }
	  
	  public void setScheduledSend(String scheduledSend) { this.scheduledSend =
	  scheduledSend; }
	 

	@Override
	public String toString() {
		return "EmailMaster [emailMasterId=" + emailMasterId + ", subject=" + subject + ", body=" + body + ", sendAt="
				+ sendAt + ", sendType=" + sendType + ",  emailDetails="
				+ emailDetails + ", emailAttachments=" + emailAttachments + "]";
	}

	

}
