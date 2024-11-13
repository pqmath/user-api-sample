package com.br.matthew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.matthew.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
