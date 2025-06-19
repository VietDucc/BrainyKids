package com.example.demo.repository;

import com.example.demo.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
}
