package com.foodei.project.entity;

import com.foodei.project.dto.BlogInfo;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@SqlResultSetMapping(
        name = "listBlogInfo",
        classes = @ConstructorResult(
                targetClass = BlogInfo.class,
                columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "slug", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "thumbnail", type = String.class),
                        @ColumnResult(name = "published_at", type = String.class),
                        @ColumnResult(name = "count_comment", type = Integer.class),
                        @ColumnResult(name = "author", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "getAllBlogInfo",
        resultSetMapping = "listBlogInfo",
        query = "SELECT b.id, b.title, b.slug, b.description, b.content, b.thumbnail,\n" +
                "       DATE_FORMAT(b.published_at, '%d/%m/%Y') as published_at,\n" +
                "       json_object('id', u.id, 'name', u.name) as author,\n" +
                "       COUNT(c.id) as count_comment\n" +
                "from blog b\n" +
                "         inner join `user` u on b.user_id = u.id\n" +
                "         inner JOIN comment c on b.id = c.blog_id\n" +
                "where b.status = 1\n" +
                "GROUP by b.id\n" +
                "ORDER BY b.published_at DESC;"
)
//@SqlResultSetMapping(
//        name = "listBlogByCategoryName",
//        classes = @ConstructorResult(
//                targetClass = BlogInfoBySomething.class,
//                columns = {
//                        @ColumnResult(name = "id", type = String.class),
//                        @ColumnResult(name = "title", type = String.class),
//                        @ColumnResult(name = "slug", type = String.class),
//                        @ColumnResult(name = "description", type = String.class),
//                        @ColumnResult(name = "thumbnail", type = String.class),
//                        @ColumnResult(name = "published_at", type = String.class),
//                        @ColumnResult(name = "count_comment", type = Integer.class),
//                        @ColumnResult(name = "author", type = String.class),
//                        @ColumnResult(name = "categoryName", type = String.class)
//                }
//        )
//)
//@NamedNativeQuery(
//        name = "getBlogsByCategoryName",
//        resultSetMapping = "listBlogByCategoryName",
//        query = "SELECT b.id, b.title, b.slug, b.description, b.thumbnail,\n" +
//                "       DATE_FORMAT(b.published_at, '%d/%m/%Y') as published_at,\n" +
//                "       json_object('id', u.id, 'name', u.name) as author,\n" +
//                "       COUNT(c.id) as count_comment,\n" +
//                "       c2.name as categoryName\n" +
//                "from blog b\n" +
//                "         left join `user` u on b.user_id = u.id\n" +
//                "         LEFT JOIN comment c on b.id = c.blog_id\n" +
//                "         LEFT join blog_categories bc on b.id = bc.blog_id\n" +
//                "         LEFT JOIN category c2 on bc.categories_id = c2.id\n" +
//                "where b.status = 1 and c2.name = :name\n" +
//                "GROUP by b.id\n" +
//                "ORDER BY b.published_at DESC"
//)
//@SqlResultSetMapping(
//        name = "listBlogByUserName",
//        classes = @ConstructorResult(
//                targetClass = BlogInfoBySomething.class,
//                columns = {
//                        @ColumnResult(name = "id", type = String.class),
//                        @ColumnResult(name = "title", type = String.class),
//                        @ColumnResult(name = "slug", type = String.class),
//                        @ColumnResult(name = "description", type = String.class),
//                        @ColumnResult(name = "thumbnail", type = String.class),
//                        @ColumnResult(name = "published_at", type = String.class),
//                        @ColumnResult(name = "count_comment", type = Integer.class),
//                        @ColumnResult(name = "author", type = String.class),
//                        @ColumnResult(name = "userName", type = String.class)
//                }
//        )
//)
//@NamedNativeQuery(
//        name = "getBlogsByUserName",
//        resultSetMapping = "listBlogByUserName",
//        query = "SELECT b.id, b.title, b.slug, b.description, b.thumbnail,\n" +
//                "       DATE_FORMAT(b.published_at, '%d/%m/%Y') as published_at,\n" +
//                "       json_object('id', u.id, 'name', u.name) as author,\n" +
//                "       COUNT(c.id) as count_comment,\n" +
//                "       u.name as userName\n" +
//                "from blog b\n" +
//                "         left join `user` u on b.user_id = u.id\n" +
//                "         LEFT JOIN comment c on b.id = c.blog_id\n" +
//                "where b.status = 1 and u.name = :name\n" +
//                "GROUP by b.id\n" +
//                "ORDER BY b.published_at DESC;"
//)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(generator = "custom_generate")
    @GenericGenerator(name = "custom_generate", strategy = "com.foodei.project.generator.CustomIdGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedAt;

    @Column(name = "status", columnDefinition = "int default 0")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "blog_categories",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "blog", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @PrePersist
    public void prePersist() {
        createAt = LocalDateTime.now();
        updatedAt =createAt;
        if(status == 1) {
            publishedAt = updatedAt;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        if (status == 1){
            publishedAt = updatedAt;
        }
    }

    @PreRemove
    public void preRemove() {
        this.categories = null;

        this.comments.forEach(comment -> comment.setBlog(null));
        this.comments = null;
    }


}