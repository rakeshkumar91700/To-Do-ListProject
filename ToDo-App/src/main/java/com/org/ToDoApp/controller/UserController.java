package com.org.ToDoApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.org.ToDoApp.dto.User;
import com.org.ToDoApp.service.ToDoService;
import com.org.ToDoApp.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userservice;
	@Autowired
	ToDoService service;
	
	
	@GetMapping("/registerUser")
	public String registation(Model model) {
		model.addAttribute("user",new User());
		
		return "RegistationForm";
	}
	
	@PostMapping("/register")
	public String saveUser(@ModelAttribute User user , RedirectAttributes redirectAttributes) {
		 if (userservice.findByEmail(user.getEmail()) != null) {
		        redirectAttributes.addFlashAttribute("message", "Email already exists");
		    } else if (userservice.saveUser(user)) {
		        redirectAttributes.addFlashAttribute("message", "Account created");
		        return "redirect:/login";
		    } else {
		        redirectAttributes.addFlashAttribute("message", "Something went wrong");
		    }
		    
		    return "redirect:/registerUser";
	}
	
}
