package com.example.demo.service;

import com.example.demo.dto.request.VocabularyRequest;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.Vocabulary;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.VocabularyRepository;
import io.jsonwebtoken.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<Vocabulary> getVocabularyByLessonId(long lesson_id) {
        return vocabularyRepository.findByLesson_id(lesson_id);
    }

//    public Vocabulary createVocabulary(Long lessonId, Vocabulary request) {
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new RuntimeException("Lesson not found"));
//
//        Vocabulary vocabulary = Vocabulary.builder()
//                .Eng(request.getEng())
//                .Vie(request.getVie())
//                .note(request.getNote())
//                .orderVocabulary(request.getOrderVocabulary())
//                .lesson(lesson)
//                .build();
//
//        return vocabularyRepository.save(vocabulary);
//    }

    public Long createVocabulary(VocabularyRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found!"));

        Vocabulary vocab = new Vocabulary();
        vocab.setLesson(lesson);
        vocab.setEng(request.getEng());
        vocab.setVie(request.getVie());
        vocab.setNote(request.getNote());
        vocab.setOrderVocabulary(request.getOrderVocabulary());

        vocabularyRepository.save(vocab);

        return vocab.getId();
    }

    public void importVocabulariesFromExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Set<Long> updatedLessonIds = new HashSet<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                long lessonId = (long) row.getCell(0).getNumericCellValue();
                updatedLessonIds.add(lessonId);

                String eng = row.getCell(1).getStringCellValue();
                String vie = row.getCell(2).getStringCellValue();
                String note = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null;

                VocabularyRequest request = VocabularyRequest.builder()
                        .lessonId(lessonId)
                        .Eng(eng)
                        .Vie(vie)
                        .note(note)
                        .orderVocabulary(i - 1)
                        .build();

                createVocabulary(request);
            }

        } catch (IOException | java.io.IOException e) {
            throw new RuntimeException("Failed to import vocabularies from Excel", e);
        }
    }

    public void deleteVocabulary(Long vocabulary_id) {
        vocabularyRepository.deleteById(vocabulary_id);
    }

    public Optional<Vocabulary> getVocabularyById(Long vocabulary_id) {
        return vocabularyRepository.findById(vocabulary_id);
    }
}
