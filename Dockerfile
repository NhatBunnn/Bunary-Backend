FROM eclipse-temurin:21-jdk
WORKDIR /app

# DÙNG JAR ĐÃ ĐÓNG GÓI CHẠY ĐƯỢC
COPY target/bunary-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]