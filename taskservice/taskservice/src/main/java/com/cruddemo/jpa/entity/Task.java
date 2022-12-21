
package com.cruddemo.jpa.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity

@Table(name = "task")
public class Task implements Serializable {

	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	private String taskTitle;

	private String asignee;
	private String createdBy;
    private String path;
    
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Task parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Task> children = new HashSet<Task>();

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getAsignee() {
		return asignee;
	}

	public void setAsignee(String asignee) {
		this.asignee = asignee;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public Set<Task> getChildren() {
		return children;
	}

	public void setChildren(Set<Task> children) {
		this.children = children;
	}

	public void addChild(Task child) {
		this.children.add(child);
	}

	public Task(String taskTitle, String asignee, String createdBy, Task parent,String path) {
		super();
		this.taskTitle = taskTitle;
		this.asignee = asignee;
		this.createdBy = createdBy;
		this.parent = parent;
		this.path = path;
	}

	public Task(long taskId) {
		super();
		this.taskId = taskId;
	}

	public Task(String taskTitle, String asignee, String createdBy) {
		super();
		this.taskTitle = taskTitle;
		this.asignee = asignee;
		this.createdBy = createdBy;
	}

	public Task() {
		super();
	}

	public Task(Long taskId, String taskTitle, String asignee, String createdBy,String path) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.asignee = asignee;
		this.createdBy = createdBy;
		this.path = path;
	}

}
