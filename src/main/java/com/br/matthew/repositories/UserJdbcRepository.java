package com.br.matthew.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.br.matthew.entity.User;

@Repository
public interface UserJdbcRepository {
	
	User create(User user);
	User update(User user);
	void deleteById(Long id);
	User findById(Long id);
	List<User> findAll();

}
