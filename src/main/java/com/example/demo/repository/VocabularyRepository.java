package com.example.demo.repository;

import com.example.demo.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByLesson_id(long lesson_id);
    Vocabulary findById(long id);
}
