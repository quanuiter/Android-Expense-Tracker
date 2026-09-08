# SpendWise

SpendWise là ứng dụng Android quản lý chi tiêu cá nhân theo hướng offline-first, có nhận dạng hóa đơn bằng ML Kit. Đây là sườn kỹ thuật ban đầu cho đồ án; phần local CRUD và ngân sách đã có đường dữ liệu thật, còn Firebase và camera/OCR được để thành các mốc triển khai tiếp theo.

## Trạng thái hiện tại

Đã có:

- Project Android Kotlin + Jetpack Compose, một `app` module.
- Material 3 theme và Navigation Compose.
- Màn hình vào chế độ local và năm tab chính.
- Room database cho giao dịch và ngân sách tháng.
- Repository offline-first và Hilt dependency injection.
- Thêm giao dịch chi tiêu; danh sách và dashboard cập nhật từ Room.
- Đặt/cập nhật ngân sách tháng; dashboard tính số còn lại.
- Contract ban đầu cho Authentication, receipt parser và category suggester.
- Dependency CameraX, ML Kit, WorkManager và Firebase main modules.

Chưa có:

- Firebase Authentication/Firestore và `google-services.json`.
- Camera preview, lưu ảnh và gọi ML Kit.
- Receipt parser thực tế và màn hình xác nhận OCR.
- Sửa/xóa giao dịch, biểu đồ, cảnh báo, export và đồng bộ.

## Chạy project

Yêu cầu:

- Android Studio tương thích AGP 8.13.x.
- JDK 17.
- Android SDK 36.

Các bước:

1. Mở thư mục repository bằng Android Studio.
2. Chọn Gradle JDK 17 trong `Settings > Build Tools > Gradle`.
3. Cài Android SDK 36 nếu IDE yêu cầu.
4. Sync Gradle và chạy cấu hình `app` trên emulator/device API 26+.
5. Chọn **Tiếp tục ở chế độ local** để thử luồng Room hiện tại.

Máy hiện tại đang trỏ `PATH` vào Java 8 và chưa có Android SDK. Có JDK 23 ở một đường dẫn khác để kiểm tra Gradle, nhưng cách đơn giản nhất khi phát triển là dùng JDK 17 đi kèm Android Studio và cài Android SDK 36.

## Quy ước quan trọng

- Tiền dùng `Long` theo đơn vị nhỏ nhất; với VND, `100000` nghĩa là 100.000 ₫. Không dùng `Double` cho tiền.
- Mọi dữ liệu nghiệp vụ đọc từ Room. Firestore không được cấp dữ liệu trực tiếp cho UI.
- Xóa giao dịch dùng soft delete để còn đồng bộ thao tác xóa lên cloud.
- Entity Room không đi thẳng lên UI; Repository ánh xạ sang model nghiệp vụ.
- Không commit `google-services.json`, keystore, ảnh hóa đơn hay file export.

Xem mô tả sâu hơn tại [Kiến trúc](docs/ARCHITECTURE.md), [Mô hình dữ liệu](docs/DATA_MODEL.md) và [Lộ trình](docs/ROADMAP.md).
