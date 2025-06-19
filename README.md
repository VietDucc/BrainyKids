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

- Swagger UI: https://duc-spring.ngodat0103.live/demo/swagger-ui/index.html#

## CICD Architecture Diagram
![image](https://github.com/user-attachments/assets/cabd97dc-9461-4743-9b1a-a34705bb57ea)
- Workflow uses Github Actions, triggered when a pussh is made to the Dev branch.
- The goal to automate: testing - security scanning - building - deploying the backend to the server
🧱 Prepare the environment
- checkout: Download the source code from the repository.
- Log in to Docker Hub to build and push the image.

🛡️ Scan the backend image security
- Snyk Scan: Scan the Docker image my-backend:latest for vulnerabilities.
- Trivy Scan: After building a new image, continue scanning with Trivy.

🔍 Scan the web interface security
Arachni Scan:
- Run an Arachni container to scan the web app (frontend).
- Export HTML report and deploy to GitHub Pages. (https://vietducc.github.io/BrainyKids/#!/summary/charts)
![image](https://github.com/user-attachments/assets/458836ce-8a14-499e-95c3-530a90de6b9d)

⚙️ Build and Deploy Backend
- Build Docker image from source code.
- Push image to Docker Hub.
- SSH into the server and deploy using docker-compose:
- Stop the old container, pull the new image, restart.

✅ Automated testing
- Install Java 17 to run the Spring Boot project.
- Run API tests using JUnit and RestAssured.

🔒 Security
- Sensitive information such as Docker credentials, SSH keys, tokens are stored using GitHub Secrets.
## 🛠️ Tech

- **Language**: Java 17
- **Framework**: Spring Boot
- **Authentication**: Clerk (frontend), JWT (backend)
- **Database**: PostgreSQL
- **Cache**: Redis, Caffeine
- **API Documentation**: Swagger (OpenAPI), Postman
- **Deployment**: Docker, Docker Compose
- **CI/CD**: GitHub Actions
- **Security Tools**: Trivy, Snyk, Arachni
- **Operating System**: Ubuntu Server
- **Testing**" JUnit5, RestAsured

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
  + Duong Viet Duc - MSSV: 21521959
  + Do Quang Huy - MSSV: 215221339
  + Dong Nguyen Huu Khoa - MSSV: 23520734
  + Le Trung Kien - MSSV: 21520308
- Duration: 3 months

## 🔗 Related Links
🌐 Web App: https://brainykidslearn.id.vn

🛠️ Admin Panel: https://brainykidslearn.id.vn/vi/admin

💻 Frontend Repository: https://github.com/VietDucc/brainy-kids-frontend

⚙️ Backend Repository: https://github.com/VietDucc/BrainyKids
