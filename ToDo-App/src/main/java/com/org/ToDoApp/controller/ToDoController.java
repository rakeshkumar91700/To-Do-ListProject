package com.org.ToDoApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.org.ToDoApp.dto.ToDoList;
import com.org.ToDoApp.dto.User;
import com.org.ToDoApp.service.ToDoService;
import com.org.ToDoApp.service.UserService;

import jakarta.servlet.http.HttpSession;
@Controller
public class ToDoController {
	
	@Autowired
	ToDoService todoservice;
	
	@Autowired
	UserService userservice;
	
	@GetMapping("/viewToDoList")
	public String viewAllToDoList(Model model ,@ModelAttribute("message") String message , HttpSession session) {
		
		if (session.getAttribute("loggedinUser")!=null) {
			User User= (User)session.getAttribute("loggedinUser");	
			
			List<ToDoList> todos=todoservice.getAllItemByUser(User);
			model.addAttribute("list" , todos);
			
			return "ViewToDoList";
		}else {
			return "redirect:/login";
		}
		
		
	}
	
	@GetMapping("/updateToDoStatus/{id}")
	public String updateToDoStatuse(@PathVariable int id , RedirectAttributes redirectAttributes , HttpSession session) {
	
		if (session.getAttribute("loggedinUser")!=null && todoservice.updateStatus(id)){
			redirectAttributes.addFlashAttribute("message" , "Update successfull");
			
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message" , "Update un-successfull");
		return "redirect:/viewToDoList";
	}
	
	@GetMapping("/addToDoItem")
	public String addToDoItem(Model model, HttpSession session) {
		if (session.getAttribute("loggedinUser")!= null) {
			
			ToDoList newtodolist= new ToDoList();
//			newtodolist.setUser((User)session.getAttribute("loggedinUser"));
		model.addAttribute("todo" , newtodolist);
			
		return "AddToDoItem";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/saveToDoItem") 
	public String saveToDoItem( @ModelAttribute ToDoList todo , RedirectAttributes redirectAttributes ,HttpSession session ) {
		User u= (User) session.getAttribute("loggedinUser");
			todo.setUser(u);
		if (todoservice.saveOrUpdateToDO(todo)) {
			redirectAttributes.addFlashAttribute("message", "Save Success");
				return "redirect:/viewToDoList";
					} 
			redirectAttributes.addFlashAttribute("message" ,"Save failure");
			return "redirect:/addToDoItem";
	}


	@GetMapping("/editToDoItem/{id}")
	public String editToDoItem(@PathVariable int id , Model model, HttpSession session ) {
		if (session.getAttribute("loggedinUser")!=null) {
			model.addAttribute("todo",todoservice.getToDoItemById(id));
			return "EditToDoItem";
		}
		return "redirect:/login";
	}
	@PostMapping("/editSaveToDoItem")
	public String editsaveToDoItem(ToDoList todo ,RedirectAttributes redirectAttributes, HttpSession session ) {
			User user=(User) session.getAttribute("loggedinUser");
				todo.setUser(user);
		if (todoservice.saveOrUpdateToDO(todo)) {
			redirectAttributes.addFlashAttribute("message", "Edit Success");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Edit Failure");
		return "redirect:/editToDoItem/"+todo.getId();
	}
	
	@GetMapping("/deleteToDoItem/{id}")
	public String deleteToDoItem(@PathVariable int id, RedirectAttributes redirectAttributes , HttpSession session ) {
		if (session.getAttribute("loggedinUser")!=null && todoservice.deleteItem(id)) {
			redirectAttributes.addFlashAttribute("message","Delete Success");	
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Delete failure");
		return "redirect:/viewToDoList";
		
	}

	
	
	
}
