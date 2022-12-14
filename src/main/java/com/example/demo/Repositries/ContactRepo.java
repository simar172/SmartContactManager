package com.example.demo.Repositries;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Enitities.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {
	@Query("from Contact as c where c.u.id=:uid")
	public Page<Contact> getContactsByUser(@Param("uid") int uid, Pageable p);
}
