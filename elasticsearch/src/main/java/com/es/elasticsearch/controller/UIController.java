package com.es.elasticsearch.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.es.elasticsearch.Repository.ElasticSearchQuery;
import com.es.elasticsearch.Repository.UserRepository;
import com.es.elasticsearch.entity.User;
import com.es.elasticsearch.entity.UserData;

@Controller
public class UIController {

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String viewUserPage() throws IOException {
		return "initialUserDocument";
	}

	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserData userData, @ModelAttribute User user, HttpSession session)
			throws IOException {
		saveData(userData, user, session);
		session.setAttribute("message", "User created successfully..");
		return "initialUserDocument.html";
	}

	@GetMapping("/userdashboard")
	public String viewHomePage(Model model, String keyword, @RequestParam(defaultValue = "0") int page)
			throws IOException {

		if (keyword != null) {
			model.addAttribute("listUserDocuments", elasticSearchQuery.searchByKeyword(keyword));
			
			List<User> userlist = elasticSearchQuery.searchByKeyword(keyword);
			Page<User> users = elasticSearchQuery.findPaginated(page, 10);
			//model.addAttribute("listUserDocuments", users);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", users.getTotalPages());
			model.addAttribute("totalItems", userlist.size());
		} else {
			model.addAttribute("listUserDocuments", elasticSearchQuery.searchAllDocuments());

			Page<User> users = elasticSearchQuery.findPaginated(page, 10);
			//model.addAttribute("listUserDocuments", users);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", users.getTotalPages());
			model.addAttribute("totalItems", users.getTotalElements());

		}
		return "index.html";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute UserData userData, @ModelAttribute User user, HttpSession session)
			throws IOException {
		saveData(userData, user, session);
		session.setAttribute("message", "User created successfully..");
		return "newUserDocument.html";
	}

	public void saveData(@ModelAttribute UserData userData, @ModelAttribute User user, HttpSession session)
			throws IOException {
		try {
			elasticSearchQuery.createOrUpdateDocument(user);
		} catch (Exception e) {
			userRepository.save(userData);
			session.setAttribute("message", "Saved changes successfully.. " + e);
		}
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserData userData, @ModelAttribute User user, HttpSession session)
			throws IOException {
		saveData(userData, user, session);
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
	public String deleteUser(@PathVariable(value = "id") String id, HttpSession session) throws IOException {
		this.elasticSearchQuery.deleteDocumentById(id);
		session.setAttribute("message", "User " + id + " Deleted sucessfully. Please refresh the page");
		return "index.html";
	}

	@GetMapping("/fetchUser/{id}")
	public String showUserDetails(@PathVariable(value = "id") String id, Model model) throws IOException {
		User user = elasticSearchQuery.getDocumentById(id);
		model.addAttribute("userdata", user);
		return "showUserDocument.html";
	}
}