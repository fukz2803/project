package com.foodei.project.repository;

import com.foodei.project.entity.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityCardRepository extends JpaRepository<IdentityCard, Integer> {
}