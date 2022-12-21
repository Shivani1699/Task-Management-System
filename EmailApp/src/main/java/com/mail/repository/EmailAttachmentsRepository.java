
package com.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mail.entity.EmailAttachments;

@Repository
public interface EmailAttachmentsRepository extends JpaRepository<EmailAttachments, Long> {

}
