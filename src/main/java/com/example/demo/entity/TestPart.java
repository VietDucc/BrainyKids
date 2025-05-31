package com.example.demo.entity;

import com.example.demo.enums.PartType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PartType type;

    private String description;
    private Long partOrder;
    private Long partNumber;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
}
