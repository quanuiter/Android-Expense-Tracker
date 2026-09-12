# Kế hoạch phát triển SpendWise

> Baseline: 12/09/2026  
> Thời lượng giả định: 12 tuần, 6 sprint, mỗi sprint 2 tuần  
> Quy mô nhóm: 3 thành viên

## 1. Mục tiêu sản phẩm

SpendWise là ứng dụng quản lý tài chính cá nhân theo hướng offline-first dành cho người Việt. Bản hoàn chỉnh cần giúp người dùng:

1. Quản lý thu, chi và chuyển tiền giữa nhiều ví.
2. Theo dõi ngân sách tổng và ngân sách theo danh mục.
3. Hiểu dòng tiền bằng dashboard và biểu đồ.
4. Quét hóa đơn để giảm thời gian nhập liệu.
5. Dùng được khi mất mạng và đồng bộ an toàn khi có tài khoản.
6. Chủ động sở hữu dữ liệu qua chức năng export.

Điểm khác biệt được ưu tiên là **OCR hóa đơn Việt Nam trên thiết bị kết hợp kiến trúc offline-first**. Nhóm không cố sao chép toàn bộ Money Lover, MISA hoặc Wallet trong phiên bản đầu tiên.

## 2. Baseline hiện tại

### Đã hoạt động

- Project Android Kotlin, Jetpack Compose, Material 3, Navigation và Hilt.
- Room database cho giao dịch và ngân sách tháng.
- Chế độ local bằng user tạm `local-demo-user`.
- Thêm khoản chi thủ công.
- Xem danh sách giao dịch.
- Đặt/cập nhật ngân sách tháng hiện tại.
- Dashboard hiển thị tổng chi, số còn lại và trạng thái vượt ngân sách.
- Model có `syncState`, soft delete và các trường dành cho ảnh/OCR.
- Build debug và unit test hiện tại chạy thành công.

### Mới là khung hoặc chưa có

- Sửa/xóa giao dịch trên UI, khoản thu, chuyển tiền, chọn ngày.
- Ví, danh mục tùy chỉnh, tìm kiếm và bộ lọc.
- Ngân sách theo danh mục, biểu đồ và notification.
- CameraX, ML Kit, receipt parser và màn hình xác nhận OCR.
- Firebase Authentication, Firestore và WorkManager sync.
- Giao dịch định kỳ, export, biometric và test đầy đủ.

## 3. Phạm vi phiên bản mục tiêu

### Bắt buộc

- CRUD giao dịch đầy đủ.
- Khoản thu, khoản chi và chuyển tiền.
- Nhiều ví và danh mục tùy chỉnh.
- Tìm kiếm, lọc và tổng hợp giao dịch.
- Ngân sách tổng và theo danh mục.
- Dashboard thu, chi, số dư, tài sản và biểu đồ.
- Cảnh báo ngân sách 80% và 100%.
- Scan hóa đơn end-to-end, luôn có bước xác nhận.
- Firebase Auth và đồng bộ offline-first.
- Giao dịch định kỳ.
- Export CSV.
- Migration, kiểm thử và xử lý trạng thái lỗi.

### Có thể làm nếu còn thời gian

- Ví gia đình/chia sẻ.
- PIN hoặc biometric.
- Mục tiêu tiết kiệm.
- Import CSV.
- Dark mode tùy chọn.
- Dự báo chi tiêu cuối tháng.

### Ngoài phạm vi phiên bản này

- Liên kết ngân hàng thật.
- Quản lý đầu tư, vàng hoặc chứng khoán.
- Thuế thu nhập cá nhân.
- AI chatbot tài chính.
- Quản lý vay/nợ phức tạp.
- Đa tiền tệ và tỷ giá trực tuyến.

## 4. Ownership

| Thành viên | Ownership chính | Trách nhiệm |
|---|---|---|
| TV1 — UI/UX & giao dịch | `feature/transaction`, `feature/wallet`, `feature/auth`, `feature/profile`, navigation, design system | Luồng người dùng, form, danh sách, filter, accessibility và trạng thái UI |
| TV2 — Data & cloud | `core/database`, `core/data`, `core/di`, Firebase, WorkManager, migration | Schema, DAO, repository, Auth, sync, export và test tầng dữ liệu |
| TV3 — OCR & insight | `feature/receipt`, `feature/dashboard`, `feature/budget`, notification | Camera/OCR/parser, ngân sách, biểu đồ, cảnh báo và test tính toán |

### File dùng chung

Các thay đổi sau phải được cả nhóm thống nhất trước khi merge:

- Domain model và repository interface.
- Room database version và migration.
- Firestore document schema và Security Rules.
- Navigation routes.
- Version catalog và plugin build.

TV2 là người duyệt cuối cho schema/migration. TV1 là người duyệt cuối cho navigation. Thay đổi domain contract cần ít nhất hai thành viên review.

## 5. Thứ tự phụ thuộc

```text
Data model ổn định
    ↓
CRUD + ví + danh mục
    ↓
Ngân sách + dashboard
    ↓
OCR và giao dịch định kỳ
    ↓
Firebase sync
    ↓
Export + hardening + phát hành
```

Không bắt đầu Firestore schema trước khi model giao dịch, ví và danh mục của Sprint 2 được chốt.

## 6. Kế hoạch sprint

## Sprint 1 — Sổ thu chi local hoàn chỉnh

### Mục tiêu

Hoàn thành CRUD khoản thu/chi khi không có mạng và ổn định contract giữa UI, repository và Room.

### TV1

- Màn hình chi tiết giao dịch.
- Sửa và xóa giao dịch.
- Chọn khoản thu/khoản chi.
- Date/time picker.
- Xác nhận xóa và Undo.
- Loading, success và error state.

### TV2

- Nâng repository để đọc một giao dịch, cập nhật và soft delete có `userId`.
- Bổ sung query theo khoảng ngày, type và category.
- Viết migration thay vì destructive migration.
- Test DAO, repository, mapper và converter.

### TV3

- Query tổng thu, tổng chi và số dư theo tháng.
- Dashboard hiển thị ba số tổng cơ bản.
- Test tính khoảng thời gian theo timezone và chuyển tháng.

### Điều kiện kết thúc

- Airplane mode vẫn thêm, xem, sửa và xóa được giao dịch.
- Người dùng chọn được ngày và loại thu/chi.
- Dashboard cập nhật đúng sau mọi thao tác.
- Không còn lỗi mất dữ liệu hoặc tạo trùng do nhấn lưu nhiều lần.

## Sprint 2 — Ví, danh mục, filter và ngân sách

### Mục tiêu

Đưa SpendWise từ một danh sách khoản chi thành sổ tài chính có cấu trúc.

### TV1

- UI danh sách/tạo/sửa/ẩn ví.
- UI quản lý danh mục.
- Chọn ví và danh mục trong form giao dịch.
- Tìm kiếm, filter và sort giao dịch.
- UI chuyển tiền giữa ví.

### TV2

- Thêm `WalletEntity` và `CategoryEntity`.
- Chuyển category enum sang dữ liệu Room có migration.
- Bổ sung liên kết ví vào giao dịch.
- Thiết kế chuyển tiền không làm tăng tổng thu/chi.
- Query số dư ví và query theo filter.
- Test migration và toàn vẹn số dư.

### TV3

- Ngân sách tổng và ngân sách theo danh mục.
- Chọn tháng/ngày bắt đầu/chu kỳ ngân sách.
- Màu trạng thái an toàn, sắp vượt và đã vượt.
- Notification ở ngưỡng 80% và 100%, không gửi trùng.

### Điều kiện kết thúc

- Có nhiều ví và số dư từng ví chính xác.
- Chuyển tiền không làm thay đổi tổng tài sản.
- Người dùng tạo được danh mục riêng.
- Filter và số tổng luôn dùng cùng tập dữ liệu.
- Thêm khoản chi cập nhật đúng mọi ngân sách liên quan.

## Sprint 3 — Dashboard nâng cao và OCR hóa đơn

### Mục tiêu

Tạo giá trị khác biệt: nhập liệu bằng hóa đơn và báo cáo đủ thuyết phục.

### TV1

- Camera/gallery entry UI.
- Màn hình xem lại ảnh.
- Màn hình xác nhận/chỉnh sửa kết quả OCR.
- Trạng thái đang quét, quét lỗi và nhập tay thay thế.

### TV2

- Lưu ảnh trong app-specific storage.
- Gắn URI ảnh và raw OCR text vào giao dịch.
- Dọn ảnh khi giao dịch bị xóa vĩnh viễn hoặc draft bị hủy.
- Repository API lưu giao dịch từ `ReceiptDraft`.

### TV3

- Runtime camera permission.
- CameraX preview và capture.
- Chọn ảnh từ gallery.
- ML Kit Text Recognition.
- `DefaultReceiptTextParser` cho cửa hàng, ngày và tổng tiền.
- `KeywordCategorySuggester` và confidence.
- Biểu đồ danh mục, xu hướng 6 tháng và so sánh tháng trước.

### Điều kiện kết thúc

- Luồng `ảnh → OCR → xác nhận → giao dịch` chạy end-to-end.
- Không tự lưu kết quả OCR khi người dùng chưa xác nhận.
- Có tập test tối thiểu 30 hóa đơn Việt Nam.
- Mục tiêu tham khảo: tổng tiền ≥ 90%, ngày ≥ 85%, cửa hàng ≥ 80%.
- OCR thất bại vẫn cho phép nhập giao dịch thủ công.

## Sprint 4 — Tài khoản và đồng bộ cloud

### Mục tiêu

Đồng bộ nhiều thiết bị mà không làm mất khả năng hoạt động offline.

### TV1

- Đăng ký, đăng nhập, quên mật khẩu và đăng xuất.
- Profile và trạng thái đồng bộ.
- UI lựa chọn nhập dữ liệu local vào tài khoản hoặc giữ tách biệt.
- Hiển thị lỗi xác thực và lỗi đồng bộ rõ ràng.

### TV2

- Firebase project cho môi trường dev.
- `FirebaseAuthRepository`.
- Firestore remote data source.
- Firestore Security Rules và rules test.
- WorkManager upload/download queue.
- Retry, last-write-wins, soft delete và chống tạo trùng.
- Thay demo user bằng authenticated user context.

### TV3

- Bảo đảm ngân sách, thống kê và receipt metadata hoạt động sau sync.
- Bộ kịch bản kiểm thử offline/online và hai thiết bị.
- Hoàn thiện trạng thái lỗi liên quan ảnh/OCR chưa upload.

### Điều kiện kết thúc

- User A không đọc/ghi được dữ liệu user B.
- Ghi offline rồi online lại không mất hoặc nhân đôi giao dịch.
- Thiết bị thứ hai nhận được dữ liệu sau khi đăng nhập.
- UI chỉ đọc Room; Firestore không cấp dữ liệu trực tiếp cho màn hình.

## Sprint 5 — Tự động hóa và quyền sở hữu dữ liệu

### Mục tiêu

Giảm thao tác lặp lại và cho phép người dùng mang dữ liệu ra khỏi app.

### TV1

- UI giao dịch định kỳ.
- UI export và bộ lọc phạm vi export.
- Cài đặt notification, quyền riêng tư và xóa dữ liệu.

### TV2

- Recurring transaction scheduler idempotent.
- CSV export theo khoảng ngày, ví, type và category.
- Luồng xóa tài khoản/xóa local data an toàn.
- Chuẩn bị shared wallet backend nếu Sprint 4 ổn định.

### TV3

- Dự báo chi tiêu cuối tháng.
- Số tiền có thể chi mỗi ngày để không vượt ngân sách.
- Regression test cho notification, dashboard và OCR.

### Điều kiện kết thúc

- Worker chạy lại không tạo giao dịch định kỳ trùng.
- CSV mở đúng bằng Excel/Google Sheets và khớp dữ liệu được lọc.
- Xóa tài khoản có xác nhận và không để lại dữ liệu nhạy cảm.

## Sprint 6 — Hardening và phát hành

### TV1

- Accessibility, content description và font scaling.
- Chuyển text hard-code sang string resources.
- Hoàn thiện empty/loading/error state.
- Chuẩn hóa UX và kịch bản demo.

### TV2

- Migration test, sync test, Security Rules test và recovery test.
- Tối ưu query/index và kiểm tra dữ liệu lớn.
- Chuẩn bị build release an toàn.

### TV3

- OCR regression và kiểm tra ảnh xấu.
- Analytics/budget/notification tests.
- Báo cáo độ chính xác OCR và giới hạn đã biết.

### Cả nhóm

- Không còn lỗi P0/P1.
- Chạy smoke test trên thiết bị thật và emulator API thấp/cao.
- Chuẩn bị dữ liệu demo, video và tài liệu kiến trúc.
- Kiểm tra airplane mode, đổi tháng, đổi timezone và hai thiết bị.

## 7. Definition of Done

Một công việc chỉ được đánh dấu hoàn thành khi:

- Có nghiệp vụ thật, không phải placeholder hoặc nút disabled.
- Có loading, empty, success và error state phù hợp.
- Hoạt động offline nếu thuộc luồng local.
- Có validation và chống thao tác lặp.
- Business logic có unit test.
- DAO/schema thay đổi có database/migration test.
- Không làm mất dữ liệu phiên bản trước.
- Build debug và test liên quan chạy thành công.
- Có ít nhất một thành viên khác review.
- Tài liệu và checklist được cập nhật trong cùng PR.

## 8. Chỉ số nghiệm thu phiên bản

- 100% luồng CRUD chính hoạt động ở airplane mode.
- Không tạo trùng trong các kịch bản retry/sync/recurring.
- Security Rules chặn truy cập chéo user.
- OCR được đánh giá trên ít nhất 30 hóa đơn, có báo cáo theo từng trường.
- Không có crash P0 trong kịch bản demo.
- Các phép tính thu, chi, số dư, chuyển tiền và ngân sách có test.
- Export CSV đối chiếu đúng với dữ liệu Room.

## 9. Rủi ro cần theo dõi

| Rủi ro | Biện pháp |
|---|---|
| Sửa schema liên tục | Chốt wallet/category/transaction trước Firestore |
| OCR không chính xác | Confidence, màn hình xác nhận, tập dữ liệu thật và nhập tay fallback |
| Sync làm mất/nhân đôi dữ liệu | Room là nguồn chính, operation id, idempotency và integration test |
| Một thành viên bị nghẽn | Contract nhỏ, mock/fake repository và PR ngắn |
| Phạm vi quá lớn | Giữ phần “ngoài phạm vi”; cắt shared wallet trước khi cắt test |
| Merge conflict | Ownership rõ, không cùng sửa schema/routes trong một thời điểm |

## 10. Nhịp làm việc đề xuất

- Đầu sprint: chốt contract, acceptance criteria và người review.
- Mỗi ngày: cập nhật checklist, blocker và PR đang mở.
- Giữa sprint: tích hợp nhánh, không chờ đến ngày cuối.
- Cuối sprint: demo trên APK thật, chạy test và ghi lại phần chưa đạt.
- Công việc chưa đạt Definition of Done phải chuyển sprint, không đánh dấu hoàn thành một phần.

