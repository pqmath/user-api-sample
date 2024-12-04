package com.br.matthew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.matthew.entity.User;
import com.br.matthew.service.UserJdbcService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserJdbcService service;

	@Qualifier(value = "findById")
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@Qualifier(value = "findAll")
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@Qualifier(value = "create")
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user) {
		return ResponseEntity.ok().body(service.create(user));
	}

	@Qualifier(value = "update")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@RequestBody User user) {
		return ResponseEntity.ok().body(service.update(user));
	}

	@Qualifier(value = "delete")
	@DeleteMapping("/{id}")
	public ResponseEntity<User> delete(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
