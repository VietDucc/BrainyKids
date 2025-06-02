package com.example.demo.entity;

import com.example.demo.enums.PartType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "part_order")
    private int partOrder;

    @Column(name = "part_number")
    private int partNumber;

    @Enumerated(EnumType.STRING)
    private PartType type;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference
    private Exam exam;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    public Part(String description, int partOrder, int partNumber, PartType type) {
        this.description = description;
        this.partOrder = partOrder;
        this.partNumber = partNumber;
        this.type = type;
        this.questions = new ArrayList<>();
    }
}