package com.tms.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.file.entity.FileMaster;

@Repository
public interface FileRepository extends JpaRepository<FileMaster, Long> {
}
