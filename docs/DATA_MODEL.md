# Mô hình dữ liệu

## Transaction

| Trường | Kiểu | Ý nghĩa |
|---|---|---|
| `id` | UUID string | ID ổn định giữa local và cloud |
| `userId` | string | Chủ sở hữu; hiện dùng user demo, sau dùng Firebase UID |
| `type` | enum | `EXPENSE` hoặc `INCOME` |
| `amountMinor` | long | Số tiền nguyên, tránh sai số số thực |
| `currencyCode` | string | ISO 4217, mặc định `VND` |
| `category` | enum | Danh mục chuẩn của ứng dụng |
| `merchantName` | nullable string | Tên cửa hàng nhập tay hoặc từ OCR |
| `note` | nullable string | Ghi chú người dùng |
| `occurredAt` | instant | Thời điểm phát sinh giao dịch |
| `receiptImageUri` | nullable string | URI ảnh local, không phải raw bitmap |
| `rawOcrText` | nullable string | Text gốc để debug/cải thiện parser |
| `syncState` | enum | Trạng thái trong hàng đợi đồng bộ |
| `createdAt` | instant | Thời điểm tạo |
| `updatedAt` | instant | Thời điểm sửa gần nhất |
| `deletedAt` | nullable instant | Soft delete phục vụ sync |

Các index đầu tiên phục vụ truy vấn giao dịch theo user/thời gian và lấy hàng đợi sync theo user.

## MonthlyBudget

MVP có một ngân sách tổng cho mỗi user trong mỗi tháng. Cặp `(userId, month)` là duy nhất.

| Trường | Kiểu | Ý nghĩa |
|---|---|---|
| `id` | UUID string | ID đồng bộ |
| `userId` | string | Chủ sở hữu |
| `month` | `YearMonth` | Ví dụ `2026-09` |
| `limitMinor` | long | Hạn mức tháng |
| `currencyCode` | string | Mặc định `VND` |
| `syncState` | enum | Trạng thái đồng bộ |
| `updatedAt` | instant | Phục vụ resolve conflict |

Ngân sách theo từng category là phần mở rộng. Không đưa vào schema v1 để tránh UI và luật cảnh báo phức tạp quá sớm.

## ReceiptDraft

`ReceiptDraft` không phải Room entity. Đây là dữ liệu tạm giữa OCR và màn hình xác nhận:

- merchant dự đoán;
- ngày mua dự đoán;
- tổng tiền dự đoán;
- category gợi ý;
- raw OCR text;
- URI ảnh.

Chỉ khi người dùng xác nhận, draft mới chuyển thành transaction.

## Quy tắc migration

- Không bật `fallbackToDestructiveMigration` vì có thể làm mất dữ liệu tài chính.
- Mỗi lần đổi entity phải tăng database version và viết migration.
- Schema Room được export vào `app/schemas` để review và test migration.

