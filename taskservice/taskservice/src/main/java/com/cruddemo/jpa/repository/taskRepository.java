package com.cruddemo.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import com.cruddemo.jpa.entity.Task;

@Repository
public interface taskRepository extends JpaRepository<Task, Long> {

}
