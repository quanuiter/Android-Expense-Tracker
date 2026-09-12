# Checklist phát triển SpendWise

> Tài liệu kế hoạch chi tiết: [DEVELOPMENT_PLAN.md](DEVELOPMENT_PLAN.md)  
> Cập nhật gần nhất: 12/09/2026

## Cách sử dụng

- `[x]`: đã đạt toàn bộ Definition of Done.
- `[ ]`: chưa hoàn thành; thêm `— Đang làm` nếu đã bắt đầu.
- Giữ nguyên mã công việc để dễ tham chiếu trong issue, commit và PR.
- Cuối mỗi tuần cập nhật mục “Theo dõi sprint”.
- Không đánh dấu `[x]` nếu mới có UI nhưng chưa nối dữ liệu thật hoặc chưa test.

## Baseline hiện tại

- [x] BASE-01 — Project Kotlin + Compose + Material 3.
- [x] BASE-02 — Navigation và năm tab chính.
- [x] BASE-03 — Hilt dependency injection.
- [x] BASE-04 — Room database version 1.
- [x] BASE-05 — Thêm khoản chi thủ công vào Room.
- [x] BASE-06 — Danh sách giao dịch đọc dữ liệu Room.
- [x] BASE-07 — Đặt/cập nhật ngân sách tháng hiện tại.
- [x] BASE-08 — Dashboard tổng chi và số còn lại.
- [x] BASE-09 — Build debug và unit test hiện tại thành công.
- [ ] BASE-10 — Local CRUD đầy đủ.
- [ ] BASE-11 — OCR hoạt động end-to-end.
- [ ] BASE-12 — Firebase Auth và Firestore sync.

## Sprint 1 — Sổ thu chi local hoàn chỉnh

### TV1 — UI/UX & giao dịch

- [ ] TX-01 — Tạo màn hình chi tiết giao dịch.
- [ ] TX-02 — Tạo màn hình sửa giao dịch.
- [ ] TX-03 — Thêm thao tác xóa có xác nhận.
- [ ] TX-04 — Thêm Undo sau khi xóa.
- [ ] TX-05 — Cho chọn `EXPENSE` hoặc `INCOME`.
- [ ] TX-06 — Thêm date/time picker.
- [ ] TX-07 — Định dạng số tiền khi nhập.
- [ ] TX-08 — Chống nhấn nút lưu nhiều lần.
- [ ] TX-09 — Thêm loading/success/error state.

### TV2 — Data & cloud

- [ ] DATA-01 — Repository đọc giao dịch theo `id + userId`.
- [ ] DATA-02 — Repository cập nhật giao dịch.
- [ ] DATA-03 — Soft delete kiểm tra `id + userId`.
- [ ] DATA-04 — Query theo khoảng ngày.
- [ ] DATA-05 — Query theo transaction type.
- [ ] DATA-06 — Query theo category.
- [ ] DATA-07 — Test DAO CRUD và soft delete.
- [ ] DATA-08 — Test repository, mapper và converter.
- [ ] DATA-09 — Thiết lập lưu Room schema trong version control.

### TV3 — OCR & insight

- [ ] DASH-01 — Query tổng thu theo tháng.
- [ ] DASH-02 — Query tổng chi theo tháng.
- [ ] DASH-03 — Tính số dư thu trừ chi.
- [ ] DASH-04 — Hiển thị tổng thu/chi/số dư trên dashboard.
- [ ] DASH-05 — Dashboard tự đổi đúng khi sang tháng mới.
- [ ] DASH-06 — Test timezone và biên đầu/cuối tháng.

### Nghiệm thu Sprint 1

- [ ] GATE-S1-01 — CRUD thu/chi hoạt động ở airplane mode.
- [ ] GATE-S1-02 — Chọn được ngày giao dịch.
- [ ] GATE-S1-03 — Xóa không làm mất khả năng đồng bộ sau này.
- [ ] GATE-S1-04 — Dashboard cập nhật đúng sau create/update/delete.
- [ ] GATE-S1-05 — Không tạo giao dịch trùng do thao tác UI.

## Sprint 2 — Ví, danh mục, filter và ngân sách

### TV1 — UI/UX & giao dịch

- [ ] WALLET-UI-01 — Danh sách ví.
- [ ] WALLET-UI-02 — Tạo/sửa/ẩn ví.
- [ ] WALLET-UI-03 — Chọn ví trong form giao dịch.
- [ ] WALLET-UI-04 — UI chuyển tiền giữa hai ví.
- [ ] CAT-UI-01 — Danh sách danh mục.
- [ ] CAT-UI-02 — Tạo/sửa/ẩn danh mục.
- [ ] FILTER-01 — Tìm theo cửa hàng hoặc ghi chú.
- [ ] FILTER-02 — Lọc theo ngày/tháng.
- [ ] FILTER-03 — Lọc theo type, ví và danh mục.
- [ ] FILTER-04 — Sort theo ngày hoặc số tiền.
- [ ] FILTER-05 — Hiển thị tổng của kết quả đang lọc.

### TV2 — Data & cloud

- [ ] WALLET-DATA-01 — Thiết kế `WalletEntity` và index.
- [ ] WALLET-DATA-02 — Thiết kế `CategoryEntity`.
- [ ] WALLET-DATA-03 — Migration category enum sang dữ liệu Room.
- [ ] WALLET-DATA-04 — Gắn wallet vào transaction.
- [ ] WALLET-DATA-05 — Query số dư từng ví.
- [ ] WALLET-DATA-06 — Thiết kế transfer không tăng tổng thu/chi.
- [ ] WALLET-DATA-07 — Migration test từ database version 1.
- [ ] WALLET-DATA-08 — Test tính số dư và transfer.

### TV3 — OCR & insight

- [ ] BUDGET-01 — Ngân sách tổng theo kỳ.
- [ ] BUDGET-02 — Ngân sách theo danh mục.
- [ ] BUDGET-03 — Chọn tháng/kỳ ngân sách.
- [ ] BUDGET-04 — Tạo/sửa/xóa ngân sách.
- [ ] BUDGET-05 — Màu trạng thái an toàn/cảnh báo/vượt.
- [ ] NOTIFY-01 — Runtime notification permission.
- [ ] NOTIFY-02 — Notification channel.
- [ ] NOTIFY-03 — Cảnh báo ngưỡng 80%.
- [ ] NOTIFY-04 — Cảnh báo ngưỡng 100%.
- [ ] NOTIFY-05 — Chống gửi notification trùng.

### Nghiệm thu Sprint 2

- [ ] GATE-S2-01 — Số dư từng ví chính xác.
- [ ] GATE-S2-02 — Transfer không đổi tổng tài sản.
- [ ] GATE-S2-03 — Danh mục tùy chỉnh dùng được end-to-end.
- [ ] GATE-S2-04 — Filter và số tổng dùng cùng tập giao dịch.
- [ ] GATE-S2-05 — Ngân sách cập nhật đúng sau giao dịch.

## Sprint 3 — Dashboard nâng cao và OCR

### TV1 — UI/UX & giao dịch

- [ ] OCR-UI-01 — Entry chọn camera hoặc gallery.
- [ ] OCR-UI-02 — Preview ảnh hóa đơn.
- [ ] OCR-UI-03 — Màn hình xác nhận/chỉnh sửa OCR.
- [ ] OCR-UI-04 — Loading và progress khi nhận dạng.
- [ ] OCR-UI-05 — Error state và nhập tay fallback.

### TV2 — Data & cloud

- [ ] OCR-DATA-01 — Lưu ảnh vào app-specific storage.
- [ ] OCR-DATA-02 — Lưu URI và raw OCR text với giao dịch.
- [ ] OCR-DATA-03 — Dọn ảnh draft bị hủy.
- [ ] OCR-DATA-04 — Dọn ảnh không còn được tham chiếu.
- [ ] OCR-DATA-05 — Lưu `ReceiptDraft` qua repository.

### TV3 — OCR & insight

- [ ] OCR-01 — Runtime camera permission.
- [ ] OCR-02 — CameraX preview.
- [ ] OCR-03 — CameraX capture.
- [ ] OCR-04 — Chọn ảnh từ gallery.
- [ ] OCR-05 — Gọi ML Kit Text Recognition.
- [ ] OCR-06 — Parser merchant.
- [ ] OCR-07 — Parser purchase date.
- [ ] OCR-08 — Parser total amount.
- [ ] OCR-09 — Confidence cho từng trường.
- [ ] OCR-10 — Keyword category suggester.
- [ ] CHART-01 — Biểu đồ chi theo danh mục.
- [ ] CHART-02 — Xu hướng sáu tháng.
- [ ] CHART-03 — So sánh với tháng trước.

### Nghiệm thu Sprint 3

- [ ] GATE-S3-01 — `Ảnh → OCR → xác nhận → giao dịch` hoạt động.
- [ ] GATE-S3-02 — OCR không tự lưu trước xác nhận.
- [ ] GATE-S3-03 — Có ít nhất 30 mẫu hóa đơn kiểm thử.
- [ ] GATE-S3-04 — Tổng tiền đúng tối thiểu 90% trên tập test.
- [ ] GATE-S3-05 — OCR lỗi vẫn nhập tay được.

## Sprint 4 — Tài khoản và đồng bộ

### TV1 — UI/UX & giao dịch

- [ ] AUTH-UI-01 — Form đăng ký.
- [ ] AUTH-UI-02 — Form đăng nhập.
- [ ] AUTH-UI-03 — Quên mật khẩu.
- [ ] AUTH-UI-04 — Profile và đăng xuất.
- [ ] AUTH-UI-05 — Trạng thái/lần sync gần nhất.
- [ ] AUTH-UI-06 — Lựa chọn xử lý dữ liệu local khi đăng nhập.

### TV2 — Data & cloud

- [ ] AUTH-01 — Cấu hình Firebase dev.
- [ ] AUTH-02 — `FirebaseAuthRepository`.
- [ ] SYNC-01 — Chốt Firestore schema.
- [ ] SYNC-02 — Firestore Security Rules.
- [ ] SYNC-03 — Security Rules tests.
- [ ] SYNC-04 — Upload pending upsert.
- [ ] SYNC-05 — Upload pending delete.
- [ ] SYNC-06 — Download remote changes vào Room.
- [ ] SYNC-07 — WorkManager constraints và retry.
- [ ] SYNC-08 — Chống tạo trùng/idempotency.
- [ ] SYNC-09 — Conflict resolution bằng `updatedAt`.
- [ ] SYNC-10 — Thay demo user bằng authenticated user context.

### TV3 — OCR & insight

- [ ] SYNC-QA-01 — Kiểm tra ngân sách sau sync.
- [ ] SYNC-QA-02 — Kiểm tra dashboard sau sync.
- [ ] SYNC-QA-03 — Kiểm tra receipt metadata/ảnh sau sync.
- [ ] SYNC-QA-04 — Kịch bản offline rồi reconnect.
- [ ] SYNC-QA-05 — Kịch bản chỉnh sửa trên hai thiết bị.

### Nghiệm thu Sprint 4

- [ ] GATE-S4-01 — User A không truy cập được dữ liệu user B.
- [ ] GATE-S4-02 — Offline rồi reconnect không mất dữ liệu.
- [ ] GATE-S4-03 — Retry không tạo giao dịch trùng.
- [ ] GATE-S4-04 — Thiết bị thứ hai nhận được dữ liệu.
- [ ] GATE-S4-05 — UI chỉ đọc dữ liệu từ Room.

## Sprint 5 — Tự động hóa và export

### TV1 — UI/UX & giao dịch

- [ ] RECUR-UI-01 — Form giao dịch định kỳ.
- [ ] RECUR-UI-02 — Danh sách và trạng thái lịch định kỳ.
- [ ] EXPORT-UI-01 — Chọn phạm vi và bộ lọc export.
- [ ] SETTINGS-01 — Cài đặt notification.
- [ ] SETTINGS-02 — Xóa dữ liệu/tài khoản có xác nhận.

### TV2 — Data & cloud

- [ ] RECUR-01 — Model lịch định kỳ.
- [ ] RECUR-02 — Worker sinh giao dịch đến hạn.
- [ ] RECUR-03 — Idempotency khi worker retry.
- [ ] EXPORT-01 — Export CSV.
- [ ] EXPORT-02 — Kiểm tra escaping và tiếng Việt trong CSV.
- [ ] PRIVACY-01 — Xóa tài khoản và dữ liệu local an toàn.

### TV3 — OCR & insight

- [ ] FORECAST-01 — Dự báo chi cuối tháng.
- [ ] FORECAST-02 — Tính số tiền có thể chi mỗi ngày.
- [ ] REGRESSION-01 — Notification regression tests.
- [ ] REGRESSION-02 — Dashboard regression tests.
- [ ] REGRESSION-03 — OCR regression tests.

### Nghiệm thu Sprint 5

- [ ] GATE-S5-01 — Recurring worker không sinh trùng.
- [ ] GATE-S5-02 — CSV mở đúng trong Excel/Google Sheets.
- [ ] GATE-S5-03 — CSV khớp filter và dữ liệu Room.
- [ ] GATE-S5-04 — Xóa dữ liệu không để lại ảnh nhạy cảm.

## Sprint 6 — Hardening và phát hành

### TV1 — UI/UX & giao dịch

- [ ] RELEASE-UI-01 — Chuyển text hard-code sang resources.
- [ ] RELEASE-UI-02 — Accessibility và content descriptions.
- [ ] RELEASE-UI-03 — Font scaling và màn hình nhỏ.
- [ ] RELEASE-UI-04 — Hoàn thiện empty/loading/error state.
- [ ] RELEASE-UI-05 — Kịch bản demo.

### TV2 — Data & cloud

- [ ] RELEASE-DATA-01 — Chạy toàn bộ migration tests.
- [ ] RELEASE-DATA-02 — Chạy sync và Security Rules tests.
- [ ] RELEASE-DATA-03 — Kiểm tra query/index với dữ liệu lớn.
- [ ] RELEASE-DATA-04 — Kiểm tra recovery khi sync/build bị gián đoạn.
- [ ] RELEASE-DATA-05 — Chuẩn bị release build.

### TV3 — OCR & insight

- [ ] RELEASE-OCR-01 — OCR regression với ảnh mờ/chói/nghiêng.
- [ ] RELEASE-OCR-02 — Báo cáo accuracy theo từng trường.
- [ ] RELEASE-INSIGHT-01 — Test ngân sách, analytics và notification.

### Nghiệm thu phát hành

- [ ] GATE-REL-01 — Không còn bug P0/P1.
- [ ] GATE-REL-02 — CRUD hoạt động ở airplane mode.
- [ ] GATE-REL-03 — Không tạo trùng trong sync/recurring.
- [ ] GATE-REL-04 — Test trên ít nhất hai API Android.
- [ ] GATE-REL-05 — Smoke test trên thiết bị thật.
- [ ] GATE-REL-06 — APK release được tạo thành công.
- [ ] GATE-REL-07 — README, kiến trúc và data model được cập nhật.
- [ ] GATE-REL-08 — Video/demo data sẵn sàng.

## Backlog nếu còn thời gian

- [ ] EXTRA-01 — Shared wallet/ví gia đình.
- [ ] EXTRA-02 — PIN/biometric lock.
- [ ] EXTRA-03 — Mục tiêu tiết kiệm.
- [ ] EXTRA-04 — Import CSV.
- [ ] EXTRA-05 — Dark mode tùy chọn.

## Theo dõi sprint

| Sprint | Ngày bắt đầu | Ngày kết thúc | Trạng thái | Demo/PR chính | Ghi chú/blocker |
|---|---|---|---|---|---|
| Sprint 1 | TBD | TBD | Chưa bắt đầu | | |
| Sprint 2 | TBD | TBD | Chưa bắt đầu | | |
| Sprint 3 | TBD | TBD | Chưa bắt đầu | | |
| Sprint 4 | TBD | TBD | Chưa bắt đầu | | |
| Sprint 5 | TBD | TBD | Chưa bắt đầu | | |
| Sprint 6 | TBD | TBD | Chưa bắt đầu | | |

## Release notes tạm thời

Ghi lại thay đổi đáng chú ý sau mỗi sprint để dùng cho báo cáo và thuyết trình:

- Sprint 1:
- Sprint 2:
- Sprint 3:
- Sprint 4:
- Sprint 5:
- Sprint 6:

