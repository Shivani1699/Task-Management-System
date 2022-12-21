package com.mail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mail.entity.EmailDto;

@Repository
public interface EmailDtoRepository extends JpaRepository<EmailDto, Long> {

	public List<EmailDto> findByScheduledSend(String scheduledSend);
	
}
