package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String clerkUserId;

    private String firstName;
    private String lastName;
    private String email;
    private String profile_image_url;
    private int score = 0;
    private boolean isNewUser ;

    @Column(nullable = false)
    private boolean active = false;


    @Column(nullable = false)
    private String role = "USER";

    public User(String clerkUserId, String firstName, String lastName, String email, String profile_image_url, int score) {
        this.clerkUserId = clerkUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile_image_url = profile_image_url;
        this.email = email;
        this.score = score;
        this.isNewUser = isNewUser;
        this.active = false;
        this.role ="USER";


    }
}
