package com.example.shopapp.models;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}