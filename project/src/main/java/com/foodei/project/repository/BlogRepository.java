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


    //Index
    @Query("select b from Blog b where b.status = 1 order by b.publishedAt DESC")
    List<Blog> findBlogsByStatusEqualsOrderByPublishedAtDesc();

    @Query(value = "select * \n" +
            "from blog b \n" +
            "WHERE b.status = 1\n" +
            "order by b.published_at DESC \n" +
            "limit 4", nativeQuery = true)
    List<Blog> findByStatusEqualsOrderByPublishedAtDescLimit();

    @Query("""
            select b from Blog b inner join b.categories categories
            where upper(categories.name) like upper(concat('%', ?1, '%'))""")
    List<Blog> getByCategories_NameContainsIgnoreCaseAllIgnoreCase(String name);

    @Query("""
            select b from Blog b
            where upper(b.title) like upper(concat('%', ?1, '%')) and b.status = 1
            order by b.publishedAt DESC""")
    Page<Blog> getByTitleContainsIgnoreCaseAndStatusEqualsOrderByPublishedAtDesc(String title, Pageable pageable);




}