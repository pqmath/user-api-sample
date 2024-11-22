package com.br.matthew.repository;

import java.util.List;

import com.br.matthew.entity.User;

public interface UserRepositoryJdbc {
	
	void insert(User user);
	void update(User user);
	void deleteById(Long id);
	User findById(Long id);
	List<User> findAll();

}
