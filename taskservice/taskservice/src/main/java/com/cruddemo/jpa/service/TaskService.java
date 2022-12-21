package com.cruddemo.jpa.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cruddemo.jpa.entity.Task;
import com.cruddemo.jpa.repository.taskRepository;

@Service
public class TaskService {

	@Autowired
	taskRepository repository;
	    @Value("${file.upload-dir1}")
		String FILE_DIRECTORY1;

	public Task saveTask(Task task) {
		Task parentTask = repository.findById(task.getTaskId()).orElse(null);
		Task finalParentTask = parentTask;
		if (parentTask == null) {
			String filename =task.getTaskTitle();
			String path=FILE_DIRECTORY1 + filename;
			String Created_by="bharathkgowda1999@gmail.com";
			parentTask = new Task(task.getTaskId(), task.getTaskTitle(), task.getAsignee(), Created_by,path);
			finalParentTask = repository.save(parentTask);
			
	 		File folder = new File(FILE_DIRECTORY1 + filename);
	 		folder.mkdirs();
		}
		Set<Task> children = task.getChildren();
		for (Task child : children) {
			String filename =child.getTaskTitle();
			String path=FILE_DIRECTORY1 + filename;
			Task tempChild = new Task(child.getTaskTitle(), child.getAsignee(), child.getCreatedBy(), finalParentTask,path);
			finalParentTask.addChild(tempChild);
			
	 		File folder = new File(FILE_DIRECTORY1 + filename);
	 		folder.mkdirs();
		}
		Task op = null;
		try {
			op = repository.save(finalParentTask);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return op;
	}

	public List<Task> saveTasks(List<Task> tasks) {
		return repository.saveAll(tasks);
	}

	public List<Task> getTasks() {

		return repository.findAll();
	}

	public Task getTasksById(long TaskId) {
		return repository.findById(TaskId).orElse(null);
	}

	public String deleteTask(long TaskId) {
		repository.deleteById(TaskId);
		return " Task removed....! " + TaskId;
	}

	public Task updateTask(Task task, long taskId) {
		Task existingTask = repository.findById(taskId).orElse(null);
		existingTask.setTaskTitle(task.getTaskTitle());
		existingTask.setCreatedBy(task.getCreatedBy());
		existingTask.setAsignee(task.getAsignee());
		repository.save(existingTask);
		return existingTask;
	}

}
