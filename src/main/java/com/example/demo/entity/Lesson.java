package com.example.demo.entity;

import com.example.demo.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "order_lesson")
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name="unit_id", nullable=false)
    @JsonBackReference
    private Unit unit;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Challenge> challenges = new ArrayList<>();

    public Lesson(String title, int orderIndex){
        this.title = title;
        this.orderIndex = orderIndex;
        this.challenges = new ArrayList<>();
    }
}
