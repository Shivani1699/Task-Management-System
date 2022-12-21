package com.tms.file.servicelmpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tms.file.dto.FileMasterDTO;
import com.tms.file.entity.FileMaster;
import com.tms.file.entity.Task;
import com.tms.file.exception.FileStorageException;
import com.tms.file.exception.MyFileNotFoundException;
import com.tms.file.repository.FileRepository;
import com.tms.file.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private RestTemplate restTemplate;

	// --------------------- Stored file in database and
	// folder-----------------------------------------------------------------------
	@Override
	public void uploadFiles(MultipartFile file, Long id) {
		RestTemplate resteTemplate = new RestTemplate();
		String url = "http://localhost:2307/uploadMailTrigger";
		try {
			FileMaster dbFile = uploadFile(file, id);
			FileMasterDTO fileMasterDTO = convertEntityDto(dbFile);
			resteTemplate.postForObject(url, fileMasterDTO, FileMasterDTO.class);
		} catch (Exception e) {

		}
	}

	public FileMaster uploadFile(MultipartFile file, Long id) {
		FileMaster fileMaster = new FileMaster();
		String path = getPathFromTask(id);
		Date now = new Date();
		File myFile = new File(path + "\\" + file.getOriginalFilename());

		try {
			myFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(myFile);
			fos.write(file.getBytes());
			fos.close();
			fileMaster.setTaskId(id);
			fileMaster.setFileName(file.getOriginalFilename());
			fileMaster.setCreated_date(now);
			fileMaster.setFileType(file.getContentType());
			fileMaster.setSize(file.getSize());
			fileMaster.setIs_active(true);
			fileMaster.setPath(path + file.getOriginalFilename());
			fileMaster.setData(file.getBytes());
			fileMaster.setCreated_by("shivanipardeshi261@gmail.com");
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + file.getName() + ". Please try again!", e);
		}

		return fileRepository.save(fileMaster);

	}

//-------------------------------------------------------------------------------------------------------------------------------------------
	public FileMasterDTO convertEntityDto(FileMaster fileMaster) {

		FileMasterDTO fileMasterDTO = new FileMasterDTO();
		fileMasterDTO.setCreated_by(fileMaster.getCreated_by());
		fileMasterDTO.setFileName(fileMaster.getFileName());
		fileMasterDTO.setId(fileMaster.getId());
		fileMasterDTO.setTaskId(fileMaster.getTaskId());
		return fileMasterDTO;
	}

	// -----------------------------------------------Download
	// File---------------------------------------------------------------------
	@Override
	public FileMaster downloadFile(Long id) throws Exception {
		return fileRepository.findById(id).orElseThrow(() -> new Exception("File not found with Id: " + id));
	}

	// -----------------------------------------------------------------getting all
	// Files record.------------------------------------------
	@Override
	public List<FileMaster> getAllFiles() {
		List<FileMaster> file = new ArrayList<>();

		fileRepository.findAll().forEach(file::add);

		return file;
	}

	// -------------------------------------- getting Files records from using
	// Id.------------------------------------------------------
	@Override
	public Optional<FileMaster> getFile(Long id) {
		FileMaster fileMaster = null;
		Optional<FileMaster> optionalFile = fileRepository.findById(id);
		if (optionalFile.isPresent()) {
			fileMaster = optionalFile.get();
		}
		return Optional.of(fileMaster);
	}

	// ------------------------------------------deleted file In database and
	// folder-------------------------------------------------
	@Override
	public void deleteFile(Long id) {
		FileMaster fileMaster = fileRepository.findById(id).orElse(null);
		try {
			if (id == null)
				throw new MyFileNotFoundException("File not found " + id);
			String path = fileMaster.getPath();
			File myfile = new File(path);
			myfile.delete();
		} catch (MyFileNotFoundException ex) {
			throw new MyFileNotFoundException("File not found " + id);
		}
		fileRepository.deleteById(id);
	}

	// ---------------------Fetching data From Task
	// Service---------------------------------------------
	public ResponseEntity<Task> getTaskById(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-HEADER_NAME", "XYZ");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Task> responseEntity = restTemplate.exchange("/tasks/" + id, HttpMethod.GET, entity, Task.class);
		return responseEntity;
	}

	private String getPathFromTask(Long id) {
		String paths;
		ResponseEntity<Task> fileResponseEntity = getTaskById(id);
		paths = fileResponseEntity.getBody().getPath();
		return paths;
	}

//----------------------------------------------update file details---------------------------------------------------------------------
	public FileMaster updateFile(Long id, FileMaster fileMaster) {
		Date date = new Date();
		FileMaster existingFile = fileRepository.findById(id).orElse(null);
		existingFile.setFileName(fileMaster.getFileName());
		existingFile.setModified_by(fileMaster.getModified_by());
		existingFile.setModified_date(date);
		fileRepository.save(existingFile);
		return existingFile;
	}

}
