package com.br.matthew.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.matthew.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
