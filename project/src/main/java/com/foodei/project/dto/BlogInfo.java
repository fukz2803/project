package com.foodei.project.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodei.project.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BlogInfo {
    private String id;
    private String title;
    private String slug;
    private String description;
    private String content;
    private String thumbnail;
    private String publishedAt;
    private Integer countComment;
    private AuthorInfo authorInfo;

    public BlogInfo(String id, String title, String slug, String description, String content, String thumbnail, String publishedAt, Integer countComment, String authorInfo) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.content = content;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.countComment = countComment;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.authorInfo = objectMapper.readValue(authorInfo, AuthorInfo.class);
        } catch (JsonProcessingException e) {
            this.authorInfo = null;
        }


    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class AuthorInfo{
    private Integer id;
    private String name;
}
