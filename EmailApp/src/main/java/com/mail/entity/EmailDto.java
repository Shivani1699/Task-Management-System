package com.mail.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class EmailDto  implements Serializable{
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long emailMasterId;
	private String body;
	private String[] toAddress;
	private String subject;
	private String sentType;
	private Date createdAt;
	
	@JsonBackReference
	@Nullable
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = true)
	public List<Attachments> attachment;
	private Date sendAt;
	private String scheduledSend;

	public long getEmailMasterId() {
		return emailMasterId;
	}

	public void setEmailMasterId(long emailMasterId) {
		this.emailMasterId = emailMasterId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] strings) {
		this.toAddress = strings;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	

	public List<Attachments> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Attachments> attachment) {
		this.attachment = attachment;
	}

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date date) {
		this.sendAt = date;
	}

	public String getScheduledSend() {
		return scheduledSend;
	}

	public void setScheduledSend(String scheduledSend) {
		this.scheduledSend = scheduledSend;
	}

	

	@Override
	public String toString() {
		return "EmailDto [emailMasterId=" + emailMasterId + ", body=" + body + ", toAddress="
				+ Arrays.toString(toAddress) + ", subject=" + subject + ", sentType=" + sentType + ", createdAt="
				+ createdAt + ", attachment=" + attachment + ", sendAt=" + sendAt + ", scheduledSend=" + scheduledSend
				+ "]";
	}

	public EmailDto() {
		// TODO Auto-generated constructor stub
	}

	

}
