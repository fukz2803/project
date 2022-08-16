package com.foodei.project.repository;

import com.foodei.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
            select u from User u
            where upper(u.name) like upper(concat('%', ?1, '%')) or upper(u.email) like upper(concat('%', ?2, '%'))""")
    Page<User> findByNameContainsIgnoreCaseAndEmailContainsIgnoreCase(String name, String email, Pageable pageable);


}