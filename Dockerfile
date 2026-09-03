# 1. Sử dụng JRE Java 21 Alpine siêu nhẹ (chỉ ~150MB)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 2. Copy file .jar đã build sẵn từ thư mục target ở máy thật vào container
COPY target/*.jar app.jar

# 3. Chạy file .jar trực tiếp (không qua Maven)
ENTRYPOINT ["java", "-jar", "app.jar"]