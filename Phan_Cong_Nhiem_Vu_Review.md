# KẾ HOẠCH PHÂN CÔNG NHIỆM VỤ REVIEW (SRS & SOURCE CODE)
**Dự án:** Bunary-Backend (Spring Boot)
**Nhóm gồm 4 thành viên:** Phạm Mạnh Dũng, Phương Mạnh Nghĩa, Trần Văn Long Nhật, Dương Quang Sự

---

## 🌟 1. BẢNG PHÂN CÔNG NHIỆM VỤ CHI TIẾT

*(Giả định Trần Văn Long Nhật là Trưởng nhóm, người tổng hợp cuối cùng)*

### 1.1. Phạm Mạnh Dũng - Chuyên trách mảng: Người dùng & Bảo mật (User & Security)
* **Review Tài liệu (Excel Checklist):**
  * Đánh giá các yêu cầu liên quan đến Đăng ký, Đăng nhập, Quản lý tài khoản, Phân quyền (Roles).
  * Review các yêu cầu phi chức năng về bảo mật (Security requirements, mã hóa mật khẩu, JWT...).
* **Review Source Code (Thư mục):**
  * `src/.../security/`: Cấu hình Spring Security, JWT filters, authentication.
  * `src/.../user/` & `src/.../profile/`: Logic quản lý thông tin người dùng, cập nhật profile.
* **Đầu ra (Output):** Viết báo cáo đánh giá về phần Bảo mật, Quản lý User; liệt kê các lỗi (bugs), lỗ hổng bảo mật hoặc những điểm chưa khớp giữa Code và Tài liệu.

### 1.2. Phương Mạnh Nghĩa - Chuyên trách mảng: Core Business - Học tập (Learning & Wordset)
* **Review Tài liệu (Excel Checklist):**
  * Review chi tiết các Use-case và nghiệp vụ lõi (Core Business): Quá trình học tập từ vựng, tạo bộ từ (Wordset), kiểm tra tiến độ học.
  * Kiểm tra tính logic và sự đầy đủ của các quy tắc nghiệp vụ (Business Rules) liên quan đến thuật toán lặp lại ngắt quãng (nếu có) hoặc tính điểm.
* **Review Source Code (Thư mục):**
  * `src/.../learning/`: Các API và Logic xử lý việc học.
  * `src/.../wordset/`: Quản lý bộ từ vựng, danh sách từ.
* **Đầu ra (Output):** Viết báo cáo đánh giá nghiệp vụ lõi; chỉ ra các API có logic chưa tối ưu, thiếu validation hoặc sai so với Đặc tả.

### 1.3. Dương Quang Sự - Chuyên trách mảng: Tính năng mở rộng & Real-time (Social & Schedulers)
* **Review Tài liệu (Excel Checklist):**
  * Đánh giá các yêu cầu về Mạng xã hội/Tương tác (Bài viết - Post, Cửa hàng - Shop, Thông báo - Notification).
  * Review các yêu cầu hệ thống về tính thời gian thực (Real-time/Websocket) và các tác vụ chạy ngầm.
* **Review Source Code (Thư mục):**
  * `src/.../post/`, `src/.../shop/`, `src/.../notification/`.
  * `src/.../websocket/`: Cấu hình STOMP/Websocket.
  * `src/.../scheduler/` & `src/.../batchapi/`: Các cronjob đồng bộ hoặc xử lý dữ liệu định kỳ.
* **Đầu ra (Output):** Viết báo cáo về các tính năng mở rộng; kiểm tra các xử lý đa luồng, socket, logic shop/post có đúng tài liệu không.

### 1.4. Trần Văn Long Nhật - Trưởng nhóm: System, Database, Code Quality & Tổng hợp
* **Review Tài liệu (Excel Checklist):**
  * Đánh giá tổng quan Đặc tả: Giao diện, Yêu cầu hệ thống, Ràng buộc dữ liệu.
  * Review thiết kế Database (ERD) và tính nhất quán của tài liệu.
* **Review Source Code (Thư mục):**
  * `src/.../model/`, `src/.../repository/`: Cấu trúc Entity JPA, quan hệ giữa các bảng.
  * `src/.../exception/`, `src/.../config/`, `src/.../common/`: Quy chuẩn xử lý lỗi chung (Global Exception Handler), format Response API.
  * Kiểm tra cấu hình hệ thống: `application.properties`, `.env`, `docker-compose.yml`, `pom.xml`.
* **Đầu ra (Output):** Báo cáo phần System Architecture & Database.
* **Nhiệm vụ đặc biệt (Trưởng nhóm):** Thu thập báo cáo của Dũng, Nghĩa, Sự. Tổng hợp, chuẩn hóa format, ghép nối thành một **Báo cáo Review Hoàn chỉnh** và đại diện nhóm nộp bài.

---

## 📝 2. HƯỚNG DẪN THỰC THI (QUY TRÌNH DÀNH CHO NHÓM)

### Bước 1: Kick-off (Trưởng nhóm)
- Nhật gửi lại file `ChecklistReviewTaiLieuDacTaYeuCauPhanMem.xlsx` vào group nhóm và chốt kế hoạch phân công.
- Mỗi người tự mở tab/sheet tương ứng với mảng mình phụ trách trong file Excel để tiến hành đánh giá (tick checklist).

### Bước 2: Cá nhân Review & Viết báo cáo (Dũng, Nghĩa, Nhật, Sự)
- Mỗi thành viên tự tạo 1 file `Report_[TenThanhVien].docx`.
- **Cấu trúc mỗi file báo cáo cá nhân cần có:**
  1. **Tài liệu Đặc tả:** Các hạng mục đã kiểm tra (Pass / Fail / Cần làm rõ).
  2. **Source Code:** Các lỗi/vấn đề phát hiện (Chỉ rõ Tên file và Dòng code, VD: `UserWsRecentSvc.java - Line 45 thiếu validation cho dữ liệu null`).
  3. **Đề xuất:** Hướng khắc phục cho các vấn đề tìm thấy.

### Bước 3: Tổng hợp và Báo cáo (Trưởng nhóm Nhật)
- Các thành viên nộp file cá nhân cho Nhật **trước Deadline 1-2 ngày**.
- Nhật hợp nhất thành file `BaoCao_Review_Nhom_Bunary.docx`.
- Nhật viết bổ sung phần **Lời nói đầu** và **Tổng kết mức độ hoàn thiện của dự án**.
- Tiến hành nộp báo cáo cuối cùng cho giảng viên/người hướng dẫn.
