package com.cruddemo.jpa.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruddemo.jpa.entity.Task;
import com.cruddemo.jpa.repository.taskRepository;
import com.cruddemo.jpa.service.TaskService;

@RestController

public class TaskController {
	@Autowired
	private TaskService service;

	@PostMapping("/addTask")
	public Task addTask(@RequestBody Task task) throws SQLException {
		return service.saveTask(task);
	}

	@GetMapping("/tasks")
	public List<Task> findAllTasks() {
		return service.getTasks();
	}

	@GetMapping("/tasks/{taskId}")
	public Task findTaskById(@PathVariable long taskId) {
		return service.getTasksById(taskId);
	}

	@PutMapping("/{taskId}")
	public Task updateTask(@PathVariable("taskId") long taskId, @RequestBody Task task) {

		return service.updateTask(task, taskId);
	}

	@DeleteMapping("/delete/{taskId}")
	public String deleteTask(@PathVariable long taskId) {
		return service.deleteTask(taskId);
	}

}
