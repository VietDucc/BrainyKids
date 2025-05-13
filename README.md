# Brainy-Kids Backend – Interactive Learning API

Welcome to the backend of **Brainy-Kids**, an interactive web platform designed for children and language learners. This Spring Boot application provides secure and scalable REST APIs for managing learning content, quizzes, AI-powered tools, and user data.

## 🚀 Features

- ✅ User authentication and role-based access control using Clerk and JWT
- ✅ Learning modules: blogs, flashcards, quizzes, videos
- ✅ Word-connection game logic to reinforce vocabulary
- ✅ AI-powered content generation: stories, diaries, synonyms/antonyms
- ✅ Admin dashboard APIs to manage users and content
- ✅ RESTful APIs with interactive Swagger documentation
- ✅ Redis caching layer to boost performance
- ✅ Secure HTTPS communication and containerized deployment
- ✅ Automated CI/CD pipeline with GitHub Actions
- ✅ Vulnerability scanning with Trivy, Snyk, and Arachni

## 📚 API Documentation

- Swagger UI: [https://duc-spring.ngodat0103.live/demo/swagger-ui/index.html](https://duc-spring.ngodat0103.live/demo/swagger-ui/index.html)

## 🛠️ Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot
- **Authentication**: Clerk (frontend), JWT (backend)
- **Database**: PostgreSQL
- **Cache**: Redis
- **API Documentation**: Swagger (OpenAPI), Postman
- **Deployment**: Docker, Docker Compose
- **CI/CD**: GitHub Actions
- **Security Tools**: Trivy, Snyk, Arachni
- **Operating System**: Ubuntu Server

## 🔐 Authentication & Authorization

- Users authenticate through Clerk (frontend)
- JWT tokens are verified by the backend
- Role-based access: regular users vs admin
- Secure endpoints using Spring Security

## 🚢 Deployment Instructions

```bash
# Clone the repository
git clone https://github.com/VietDucc/BrainyKids.git
cd BrainyKids

# Run with Docker
docker-compose up --build
 ```
🛡️ Security Practices
Trivy: Scans Docker images for vulnerabilities

Snyk: Detects known issues in dependencies

Arachni: Tests for web application security risks

All HTTP traffic is secured via HTTPS

👥 Project Team & Responsibilities
- Team size: 4 members

- Duration: 3 months

Your responsibilities:

-   Designed and implemented core REST APIs using Spring Boot

- Integrated Clerk for authentication and configured JWT validation

- Maintained documentation using Swagger

- Optimized backend performance with Redis caching

- Set up CI/CD with GitHub Actions for automated deployment

- Performed security testing using Trivy, Snyk, and Arachni

- Assisted team coordination, progress tracking, and task delivery

## 🔗 Related Links
🌐 Web App: https://brainy-kids-frontend.vercel.app

🛠️ Admin Panel: https://brainy-kids-frontend.vercel.app/admin

💻 Frontend Repository: https://github.com/VietDucc/brainy-kids-frontend

⚙️ Backend Repository: https://github.com/VietDucc/BrainyKids