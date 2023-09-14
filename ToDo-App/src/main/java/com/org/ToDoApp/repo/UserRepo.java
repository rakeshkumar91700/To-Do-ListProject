package com.org.ToDoApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.ToDoApp.dto.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
		
	@Query("SELECT u From User u WHERE u.email=?1 AND u.password=?2")
	public User verification(String email, String password);
}
