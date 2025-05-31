package com.example.demo.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class UnitCachingService {

    @CacheEvict(value = "unitsByCourse", key = "#courseId")
    public void evictCacheForCourse(Long courseId) {
        // chỉ dùng để xóa cache
    }
}
