package com.example.project_2_namyujin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false/*, unique = true*/)
    private String username;
    @Column(nullable = false)
    private String password;

    private String email;
    private String phone;
    private String imageUrl;
}
