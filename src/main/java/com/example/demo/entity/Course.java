package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageSrc;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)//orphanRemoval tu dong xoa unit khi xoa course
    @JsonManagedReference // Danh dau day la phan quan ly them chieu chi lap qua 1 lan
    private List<Unit> units = new ArrayList<>();// Khoi tao de tranh NullPontedException khi ta chi post courses nen luc do ko co Units dan den bi loi


    public Course(String title, String imageSrc){
        this.title = title;
        this.imageSrc = imageSrc;
        this.units = new ArrayList<>();//
    }
}
