package com.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mail.entity.Attachments;

public interface AttachmentsRepository  extends JpaRepository<Attachments, Long>{

}
