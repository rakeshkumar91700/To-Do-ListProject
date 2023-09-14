package com.org.ToDoApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.ToDoApp.dto.ToDoList;
import com.org.ToDoApp.dto.User;
public interface ToDoRepo extends JpaRepository<ToDoList, Integer>{
	
	List<ToDoList> findToDoByUser(User user);
}
