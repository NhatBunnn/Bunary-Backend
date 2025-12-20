# Bunary – học tiếng Anh qua flashcard và kết nối cộng đồng học tập

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.11-red.svg)](https://maven.apache.org/)

## 📝 Giới thiệu

**Bunary** là nền tảng học tiếng Anh thông minh, tập trung vào **học từ vựng bằng flashcard**:

- Học theo **wordset** và **flashcard**, giúp ghi nhớ từ vựng hiệu quả hơn.
- Theo dõi **tiến độ học tập của người dùng** để tối ưu lộ trình học.
- Hỗ trợ **nâng cấp bằng tiền thật** hoặc dùng tính năng miễn phí.
- Cho phép **follow các bộ từ vựng** của người khác để học và trao đổi.
- Tích hợp các tính năng **thời gian thực** và **gợi ý học tập AI** trong tương lai.

## 🏗️ Kiến trúc dự án - **Module-based Architecture**

**Bunary** được tổ chức theo **module-based architecture** để tối ưu maintainability và mở rộng:

- Các module tách riêng theo **domain / chức năng**: `user`, `wordset`, `flashcard`, `payment`, `progress`.
- Mỗi module chứa đầy đủ:
  - **Entity / Model**
  - **Repository**
  - **Service**
  - **Controller**
  - **DTO / Mapper** nếu cần
- Giúp code **dễ bảo trì**, dễ **thêm tính năng mới**, và chuẩn bị cho việc mở rộng sang **microservice**.
- 💡 Hiện tại dự án đang **refactor từ type-based sang module-based**, tối ưu hóa tổ chức code và cải thiện maintainability.

## ✨ Tính năng chính

- **Quản lý Người dùng & Xác thực**:
  - Đăng ký, đăng nhập và quản lý thông tin người dùng.
  - Bảo mật bằng **Spring Security** với xác thực dựa trên **JWT (JSON Web Token)**.
  - Hỗ trợ đăng nhập qua mạng xã hội với **OAuth2 Client**.
- **Quản lý Bài viết (CRUD)**: Tạo, đọc, cập nhật và xóa bài viết.
- **API Filtering**: API hỗ trợ lọc và tìm kiếm động bằng `spring-filter`.
- **Truy vấn nâng cao**: Sử dụng **QueryDSL** để xây dựng các truy vấn cơ sở dữ liệu một cách an toàn và linh hoạt.
- **Tương tác thời gian thực**: Tích hợp **WebSocket** cho các tính năng như thông báo hoặc chat trực tiếp.
- **Quản lý File media**: Tải lên và quản lý hình ảnh/video qua dịch vụ **Cloudinary**.
- **Gửi Email**: Tích hợp **Spring Mail** để gửi email (ví dụ: xác thực tài khoản, thông báo).
- **Cơ sở dữ liệu**:
  - Sử dụng **Spring Data JPA** với **MySQL** cho dữ liệu quan hệ.
  - Tích hợp **Spring Data MongoDB** cho các nhu cầu lưu trữ dữ liệu phi cấu trúc.
- **Giao diện (Server-side)**: Sử dụng **Thymeleaf** để render một số trang phía máy chủ.

## 🛠️ Công nghệ sử dụng

- **Framework**: Spring Boot 3.4.3
- **Ngôn ngữ**: Java 21
- **Bảo mật**: Spring Security, OAuth2, JWT (Nimbus JOSE JWT)
- **Cơ sở dữ liệu**:
  - Spring Data JPA, Hibernate
  - MySQL
  - Spring Data MongoDB
- **API & Web**: Spring Web, WebSocket, Thymeleaf
- **Truy vấn**: QueryDSL, Spring Filter
- **Build Tool**: Maven
- **Lưu trữ file**: Cloudinary
- **Email**: Spring Boot Starter Mail
- **Dev Tools**: Lombok, Spring Boot DevTools
- **Cấu hình**: `dotenv-java` (hỗ trợ file `.env`)

## 🚀 Bắt đầu

### Yêu cầu hệ thống

Để chạy dự án này, bạn cần cài đặt:

- **JDK 21**
- **Maven 3.9+**
- **MySQL Server**
- **MongoDB Server**
- Tài khoản **Cloudinary** (để lấy API keys)
- Một máy chủ **SMTP** (ví dụ: Gmail) để cấu hình gửi email.

### Cài đặt và Chạy dự án

1.  **Clone repository:**

    ```bash
    git clone <your-repository-url>
    cd Bunary-backend
    ```

2.  **Tạo file cấu hình môi trường:**

    Tạo một file tên là `.env` ở thư mục gốc của dự án và điền các thông tin cần thiết. Dự án sử dụng `dotenv-java` để đọc các biến này.

    ```properties
    # Cấu hình Database (MySQL)
    DB_URL=jdbc:mysql://localhost:3306/bunblog_db
    DB_USERNAME=root
    DB_PASSWORD=your_password

    # Cấu hình Database (MongoDB)
    MONGO_DB_URI=mongodb://localhost:27017/bunblog_mongo

    # Cấu hình JWT
    JWT_SECRET=your_super_secret_key_for_jwt
    JWT_EXPIRATION=86400000

    # Cấu hình Cloudinary
    CLOUDINARY_URL=cloudinary://api_key:api_secret@cloud_name

    # Cấu hình Email (SMTP)
    EMAIL_HOST=smtp.gmail.com
    EMAIL_PORT=587
    EMAIL_USERNAME=your_email@gmail.com
    EMAIL_PASSWORD=your_app_password
    ```

    _Lưu ý: Bạn cần cập nhật các giá trị trên cho phù hợp với môi trường của bạn._

3.  **Build dự án với Maven:**

    Sử dụng Maven Wrapper được cung cấp sẵn để build dự án. Thao tác này sẽ tải các dependency cần thiết.

    ```bash
    # Trên Windows
    ./mvnw clean install

    # Trên macOS/Linux
    ./mvnw clean install
    ```

4.  **Chạy ứng dụng:**
    ```bash
    ./mvnw spring-boot:run
    ```
    Ứng dụng sẽ khởi động và chạy tại `http://localhost:8080`.

---

Chúc bạn thành công với dự án **BunBlog**!
