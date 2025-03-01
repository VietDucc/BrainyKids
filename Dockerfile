# Sử dụng OpenJDK 21 làm base image
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR (đã build bằng Maven/Gradle) vào container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Khai báo cổng 8080 (Render sẽ tự nhận diện và map port)
EXPOSE 8080

# Lệnh chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
