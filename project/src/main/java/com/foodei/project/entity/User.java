package com.foodei.project.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "custom_generate")
    @GenericGenerator(name = "custom_generate", strategy = "com.foodei.project.generator.CustomIdGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Integer state;

    @Column(name = "role")
    private Integer role;

    @Column(name = "phone")
    private String phone;

}