package com.es.elasticsearch.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.es.elasticsearch.Repository.ElasticSearchQuery;
import com.es.elasticsearch.entity.User;

@Controller
public class UIController {

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@GetMapping("/")
	public String viewHomePage(@PathVariable(value = "keyword") String keyword, Model model) throws IOException {
		model.addAttribute("listUserDocuments", elasticSearchQuery.searchAllDocuments());
		return "index.html";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute User user, HttpSession session) throws IOException {
		System.out.println(user);
		elasticSearchQuery.createOrUpdateDocument(user);
		session.setAttribute("message", "User created successfully..");
		return "newUserDocument.html";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) throws IOException {
		System.out.println(user);
		elasticSearchQuery.createOrUpdateDocument(user);
		session.setAttribute("message", "User Updated successfully..");
		return "updateUserDocument.html";
	}

	@GetMapping("/showCreateUserForm")
	public String showCreateUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "newUserDocument.html";
	}

	@GetMapping("/updateUser/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {
		User user = elasticSearchQuery.getDocumentById(id);
		model.addAttribute("user", user);
		return "updateUserDocument.html";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value = "id") String id) throws IOException {
		this.elasticSearchQuery.deleteDocumentById(id);
		return "redirect:/";
	}

	@GetMapping("/fetchUser/{id}")
	public String showUserDetails(@PathVariable(value = "id") String id, Model model) throws IOException {
		User user = elasticSearchQuery.getDocumentById(id);
		model.addAttribute("userdata", user);
		return "showUserDocument.html";
	}
	
	
	@GetMapping("/searchUser/{keyword}")
	public String searchUser(@PathVariable(value = "keyword") String keyword, Model model) throws IOException {

		if (keyword != null) {
			model.addAttribute("userlist", elasticSearchQuery.searchByKeyword(keyword));
		} else {
			model.addAttribute("userlist", elasticSearchQuery.searchAllDocuments());
		}
		return "redirect:/";
	}
	 

}