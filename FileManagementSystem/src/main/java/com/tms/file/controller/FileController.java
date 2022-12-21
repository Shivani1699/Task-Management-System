package com.tms.file.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tms.file.constants.FileConstants.DOWNLOADFILE;
import com.tms.file.constants.FileConstants.FileId;
import com.tms.file.constants.FileConstants.FileList;
import com.tms.file.constants.FileConstants.TaskId;
import com.tms.file.constants.FileConstants.UploadFile;
import com.tms.file.entity.FileMaster;
import com.tms.file.entity.Task;
import com.tms.file.service.FileService;

@RestController
public class FileController {

	Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	// upload multiple file
	@PostMapping(UploadFile.UPLOAD_FILE)
	public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile file, @RequestParam("taskId") Long id) {
		try {
			fileService.uploadFiles(file, id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new String("Uploaded the files successfully"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new String("Fail to upload files!"));
		}
	}

	// download file
	@GetMapping(DOWNLOADFILE.DOWNLOAD_FILE_ID)
	private ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
		FileMaster fileMaster = null;
		try {
			fileMaster = fileService.downloadFile(fileId);
		} catch (Exception e) {
			logger.error("file not found!!");
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileMaster.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + fileMaster.getFileName() + "\"")
				.body(new ByteArrayResource(fileMaster.getData()));
	}

	// Getting all files records
	@GetMapping(FileList.FILELIST)
	private List<FileMaster> getAllFiles() {
		return fileService.getAllFiles();
	}

	// Getting files using id
	@GetMapping(FileId.FILE_ID)
	private Optional<FileMaster> getFile(@PathVariable Long id) {
		return fileService.getFile(id);
	}

	// delete file(Hard delete)
	@DeleteMapping(FileId.FILE_ID)
	public void deleteFile(@PathVariable Long id) {
		try {
			fileService.deleteFile(id);
		} catch (Exception e) {
			logger.error("file not found!!");
		}
	}

	// update file details
	@PutMapping(FileId.FILE_ID)
	public FileMaster updateFile(@PathVariable("id") Long id, @RequestBody FileMaster fileMaster) {

		return fileService.updateFile(id, fileMaster);
	}

	// fetching data from task services
	@GetMapping(TaskId.TASK_ID)
	public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
		return fileService.getTaskById(id);
	}

}
