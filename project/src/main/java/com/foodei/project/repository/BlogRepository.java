package com.foodei.project.repository;

import com.foodei.project.dto.BlogInfo;
import com.foodei.project.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

//    @Query(name = "getAllBlogInfo", nativeQuery = true)
//    Page<BlogInfo> getAllBlogInfo(Pageable pageable);

//    @Query(name = "getAllBlogInfo", nativeQuery = true)
//    List<BlogInfo> getAllBlogInfo();


    @Query("select b from Blog b where b.status = 1")
    @Override
    List<Blog> findAll();
}