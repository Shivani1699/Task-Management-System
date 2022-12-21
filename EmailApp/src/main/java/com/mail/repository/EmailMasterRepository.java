package com.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mail.entity.EmailMaster;
@Repository
public interface EmailMasterRepository extends JpaRepository<EmailMaster, Long>{

	//public List<EmailMaster> findByScheduledSend(String scheduledSend);
	/*
	 * @Query( value = SELECT email_master.*, email_details.* ,email_attachments.*
	 * FROM email_master INNER JOIN email_details ON email_master.email_master_id =
	 * email_details.email_master_id INNER JOIN email_attachments ON
	 * email_master.email_master_id =email_attachments.email_master_id where
	 * email_master.scheduled_send = "2022-11-28 12:05:00",
	 * 
	 * nativeQuery = true ) List<Map<String, Object>> findByScheduledSend(String
	 * scheduledSend);
	 */}

