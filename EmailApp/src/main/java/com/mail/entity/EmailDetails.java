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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class EmailDetails  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long emailDetailsId;
	@NotEmpty(message="toAddress must not be EMPTY")
	private String toAddress;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_master_id", nullable = false, insertable = false, updatable = false)
	private EmailMaster emailMaster;

	public EmailMaster getEmailMaster() {
		return emailMaster;
	}

	public void setEmailMaster(EmailMaster emailMaster) {
		this.emailMaster = emailMaster;
	}

	public Long getEmailDetailsId() {
		return emailDetailsId;
	}

	public void setEmailDetailsId(Long emailDetailsId) {
		this.emailDetailsId = emailDetailsId;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	@Override
	public String toString() {
		return "EmailDetails [emailDetailsId=" + emailDetailsId + ", toAddress=" + toAddress + ", emailMaster="
				+ emailMaster + "]";
	}
	

}
