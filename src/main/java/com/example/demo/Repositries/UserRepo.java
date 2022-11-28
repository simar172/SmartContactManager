package com.example.demo.Repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Enitities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
}
