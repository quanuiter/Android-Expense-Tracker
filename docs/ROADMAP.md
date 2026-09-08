# Lộ trình triển khai đề xuất

## Sprint 1 — nền tảng local

- Hoàn thiện create/read/update/delete giao dịch.
- Validation số tiền, ngày và nội dung.
- Danh sách có filter theo tháng/category.
- Ngân sách tháng và tính phần trăm đã dùng.
- Unit test DAO, mapper và repository.

Điều kiện hoàn thành: bật airplane mode vẫn dùng được toàn bộ luồng nhập tay và dashboard.

## Sprint 2 — hóa đơn

- CameraX preview và permission flow.
- Chụp ảnh vào app-specific storage.
- ML Kit bundled text recognition.
- Parser cho merchant, ngày, tổng tiền theo hóa đơn Việt Nam.
- Gợi ý category bằng keyword scoring.
- Màn hình xác nhận/chỉnh sửa trước khi lưu.

Điều kiện hoàn thành: demo được ít nhất 10 mẫu hóa đơn, ghi nhận accuracy từng trường thay vì chỉ nhận xét cảm tính.

## Sprint 3 — tài khoản và cloud

- Firebase project cho dev.
- Email/password Authentication.
- Thay user demo bằng Firebase UID.
- Firestore security rules: user chỉ đọc/ghi document của mình.
- WorkManager upload hàng đợi và download thay đổi.
- Kiểm thử mất mạng, retry và đăng nhập trên thiết bị thứ hai.

Điều kiện hoàn thành: local luôn dùng được; sync lỗi không làm mất hoặc nhân đôi giao dịch.

## Sprint 4 — hoàn thiện đồ án

- Biểu đồ theo category và xu hướng tháng.
- Cảnh báo 80%/100% ngân sách.
- Export CSV; PDF nếu còn thời gian.
- Accessibility, loading/error/empty states.
- Test, dữ liệu demo và kịch bản thuyết trình.

## Phân chia ownership gợi ý

| Thành viên | Ownership chính | Điểm giao với nhóm |
|---|---|---|
| 1 | Auth, navigation, transaction UI/CRUD | Dùng repository contract, review UX OCR |
| 2 | Room, budget, notification, sync | Cung cấp Flow và trạng thái sync cho UI |
| 3 | Camera, OCR/parser, dashboard/analytics | Tạo `ReceiptDraft`, dùng transaction repository |

Mỗi pull request nên nhỏ, build được và không sửa file ngoài ownership nếu chưa trao đổi. Model/domain contract cần được cả nhóm review vì ảnh hưởng mọi feature.

