package com.es.elasticsearch.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.es.elasticsearch.Repository.ElasticSearchQuery;
import com.es.elasticsearch.entity.User;

@RestController
public class ElasticSearchController {

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@PostMapping("/createOrUpdateDocument")
	public ResponseEntity<Object> createOrUpdateDocument(@RequestBody User user) throws IOException {
		String response = elasticSearchQuery.createOrUpdateDocument(user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/searchDocument")
	public ResponseEntity<Object> searchAllDocument() throws IOException {
		List<User> users = elasticSearchQuery.searchAllDocuments();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/searchByKeyword")
	public ResponseEntity<Object> searching(@RequestParam String keyword) throws IOException {
		List<User> users = elasticSearchQuery.searchByKeyword(keyword);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/getDocument")
	public ResponseEntity<Object> getDocumentById(@RequestParam String userId) throws IOException {
		User user = elasticSearchQuery.getDocumentById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/deleteDocument")
	public ResponseEntity<Object> deleteDocumentById(@RequestParam String userId) throws IOException {
		String response = elasticSearchQuery.deleteDocumentById(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
