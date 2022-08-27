package com.foodei.project.repository;

import com.foodei.project.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}