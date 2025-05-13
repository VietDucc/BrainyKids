🧠 Brainy-Kids Backend – Spring Boot REST API
Welcome to the backend service of Brainy-Kids, an interactive language learning platform designed for children and language learners. This Spring Boot application provides secure, scalable, and feature-rich APIs to support learning modules, authentication, quiz handling, AI-generated content, and admin functionalities.

🚀 Features
✅ User authentication & authorization (JWT + Clerk)
✅ AI-powered features: vocabulary context generation, synonym/antonym lookup
✅ Learning modules: blogs, flashcards, quizzes, videos, games
✅ Custom word-connection game logic
✅ Admin dashboard APIs for managing content and users
✅ RESTful API design with Swagger documentation
✅ Caching with Redis for optimized performance
✅ Secure deployment with HTTPS, Docker, CI/CD
✅ Security scanning using Trivy, Snyk, Arachni

🌐 API Documentation
Swagger UI:
👉 https://duc-spring.ngodat0103.live/demo/swagger-ui/index.html

🛠️ Tech Stack
Layer	Technology
Language	Java 17
Framework	Spring Boot (RESTful API)
Auth	Clerk, JWT
Database	PostgreSQL
Caching	Redis
API Docs	Swagger (OpenAPI), Postman
Deployment	Docker, Docker Compose
CI/CD	GitHub Actions
Security	HTTPS, Trivy, Snyk, Arachni
OS	Ubuntu Server

📁 Project Structure
📦brainy-kids-backend
 ┣ 📂src/main/java/com/brainykids
 ┃ ┣ 📂config             # Security & App configuration
 ┃ ┣ 📂controller         # REST API controllers
 ┃ ┣ 📂dto                # Data transfer objects
 ┃ ┣ 📂entity             # JPA entities
 ┃ ┣ 📂repository         # Spring Data JPA interfaces
 ┃ ┣ 📂service            # Business logic
 ┃ ┗ 📜BrainyKidsApp.java # Main Spring Boot entry
 ┣ 📂resources
 ┃ ┣ 📜application.yml    # Environment config
 ┃ ┗ 📜static/             # Static assets (if any)
 ┣ 📜Dockerfile
 ┣ 📜docker-compose.yml
 ┗ 📜README.md
🔐 Authentication & Authorization
Clerk is used as the identity provider (frontend handles UI)
Backend verifies JWT tokens from Clerk and grants role-based access
Middleware security filters ensure safe API access

⚙️ Deployment
This project is containerized and deployed via:
Docker + Docker Compose: for local and production containers
GitHub Actions: for automatic CI/CD on push
HTTPS enabled for secure connections

🧪 Security
🔍 Vulnerability Scanning:

Trivy: image scanning for known CVEs

Snyk: library dependency scanning

Arachni: runtime web app security validation

📦 Installation (Local Development)
# Clone the repo
git clone https://github.com/VietDucc/BrainyKids.git

cd BrainyKids

# Run with Docker Compose
docker-compose up --build
Ensure PostgreSQL and Redis are accessible, or use the provided docker-compose.yml.

🧠 Contributors
👨‍💻 Team size: 4 members
🔗 Project lead frontend repo: https://github.com/VietDucc/brainy-kids-frontend

🔗 Useful Links
🌐 Live Web App: https://brainy-kids-frontend.vercel.app

🔐 Admin Dashboard: https://brainy-kids-frontend.vercel.app/admin

📘 Frontend GitHub: https://github.com/VietDucc/brainy-kids-frontend

📘 Backend GitHub: https://github.com/VietDucc/BrainyKids
