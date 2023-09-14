package com.org.ToDoApp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.org.ToDoApp.dto.LoginForm;
import com.org.ToDoApp.dto.User;
import com.org.ToDoApp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
		
	@Autowired
	UserService userservice;
	
	@GetMapping({"/","/login"})
	public String goToLoginForm(Model model) {
		
		model.addAttribute("loginform", new LoginForm());
		return "Login";
	}
	
	@PostMapping("/validation")
	public String validateLogin(@RequestParam String username, @RequestParam String password, HttpSession session , RedirectAttributes redirectAttributes) {
		User validatedUser = userservice.validator(username, password);
		
		if (validatedUser != null) {
			session.setAttribute("loggedinUser", validatedUser);
			
			session.setAttribute("loggedinUserEmail", validatedUser.getEmail());
			redirectAttributes.addFlashAttribute("message","login success");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message" , "login failed");
		return "redirect:/login";
		
	}
	
	@GetMapping("/logoutUser")
	public String logOutUser(HttpSession session ,RedirectAttributes redirectAttributes) {
		session.invalidate();
		redirectAttributes.addFlashAttribute("message","looged out");
		return "redirect:/login";
	}
	
}
