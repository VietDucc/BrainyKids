package com.example.demo.config.caffein;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean
    public Caffeine<Object, Object> caffeinesConfig(){
        return Caffeine.newBuilder()
                .maximumSize(100)// toi da 100 lesson cache
                .expireAfterWrite(30, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine){
        CaffeineCacheManager manager = new CaffeineCacheManager("challengeByLesson");
        manager.setCaffeine(caffeine);
        return manager;
    }
}
