package com.example.demo.config.cloudinary;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        String cloudinaryUrl = "cloudinary://724727327455295:dR7dEItKsB68mYR2jDewZe4YZ9w@dk4lf4hcu";
        return new Cloudinary(cloudinaryUrl);
    }
}
