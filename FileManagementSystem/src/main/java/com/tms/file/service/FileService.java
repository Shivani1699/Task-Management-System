package com.tms.file.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.tms.file.entity.FileMaster;
import com.tms.file.entity.Task;

public interface FileService {
	public void uploadFiles(MultipartFile file, Long id);

	public FileMaster downloadFile(Long fileId) throws Exception;

	public List<FileMaster> getAllFiles();

	public void deleteFile(Long id);

	public Optional<FileMaster> getFile(Long id);

	public ResponseEntity<Task> getTaskById(Long id);

	public FileMaster updateFile(Long id, FileMaster fileMaster);

}
