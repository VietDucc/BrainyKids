package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "order_lesson")
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name="unit_id", nullable=false)
    @JsonBackReference
    private Unit unit;

    public Lesson(String title, int orderIndex){
        this.title = title;
        this.orderIndex = orderIndex;
    }
}
