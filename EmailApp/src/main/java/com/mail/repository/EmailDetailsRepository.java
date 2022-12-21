
package com.mail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mail.dto.SendEmailParams;
import com.mail.entity.EmailDetails;

@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails, Long> {

//	List<SendEmailParams> findByScheduledSend(String scheduledSend);
	/*
	 * @Query(
	 * "SELECT ed FROM EmailMaster em join em.emaildetails ed WHERE em.emailMasterId = :emailMasterId"
	 * ) List<EmailDetails> findDetailsByMasterId(@Param("emailMasterId") Long
	 * emailMasterId);
	 */
	
		
}
