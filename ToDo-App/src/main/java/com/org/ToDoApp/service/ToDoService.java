package com.org.ToDoApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.ToDoApp.dto.ToDoList;
import com.org.ToDoApp.dto.User;
import com.org.ToDoApp.repo.ToDoRepo;


@Component
public class ToDoService {
	
	@Autowired
	ToDoRepo repo;
	
	
	public boolean saveOrUpdateToDO(ToDoList todo) {
		ToDoList task= repo.save(todo);
	if (getToDoItemById(task.getId())!=null) {
		return true;
		}
	return false;
	}
	
	
	public ToDoList getToDoItemById(int id){
		
		return repo.findById(id).get();
	}
	
 /*	public List<ToDoList> getAllItem() {
		List<ToDoList> findAll = repo.findAll();
		return findAll;
		
	}*/
	
	public List<ToDoList> getAllItemByUser(User user){
		return repo.findToDoByUser(user);
	}
	
	public boolean deleteItem(int id) {
		repo.deleteById(id);
		if (repo.findById(id)==null) {
			return true;
		}
		return false;
		
	}
	public boolean updateStatus(int id) {
		ToDoList todo = getToDoItemById(id);
		todo.setStatus("completed");
		return saveOrUpdateToDO(todo);
	}
}
