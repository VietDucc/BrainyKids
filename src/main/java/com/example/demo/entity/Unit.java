package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "order_unit")
    private int orderUnit;

    @ManyToOne
    @JoinColumn(name= "course_id", nullable=false)  // Dam bao khoa ngoai khong null
    @JsonBackReference  // ✅ Đánh dấu đây là phần bị bỏ qua để tránh vòng lặp
    private Course course;
}
