package com.org.ToDoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.ToDoApp.dto.User;
import com.org.ToDoApp.repo.UserRepo;

@Component
public class UserService {
	@Autowired
	UserRepo uro;
	
	public boolean saveUser(User user) {
		User u=uro.save(user);
		
		if (findByEmail(u.getEmail())!= null) {
			return true;
		}
		return false;		
	}
	public User findByEmail(String email) {
	
		return uro.findByEmail(email);
	}	
	
	public User validator(String email, String password) {
		User verification = uro.verification(email, password);
		if (verification != null) {
			return verification;
		}
			return null;
	}

}
