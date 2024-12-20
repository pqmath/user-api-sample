package com.br.matthew.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.matthew.entity.User;
import com.br.matthew.exceptions.ExceptionHandler;
import com.br.matthew.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ExceptionHandler("User not found."));
	}

	public User create(User user) {
		return repository.save(user);
	}

	public User update(User user) {

		User userDb = repository.findById(user.getId()).orElseThrow(() -> new ExceptionHandler("User not found."));

		userDb.setName(user.getName());
		userDb.setSurname(user.getSurname());
		return repository.save(userDb);
	}

	public void deleteById(Long id) {
		var userDb = repository.findById(id);
		if (userDb.isEmpty()) {
			throw new ExceptionHandler("This user doesn't exist!");
		}
		repository.deleteById(id);
	}
}
