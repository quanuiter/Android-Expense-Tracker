# Kế hoạch phát triển tổng thể SpendWise

> Baseline: 12/09/2026
>
> Quy mô dự kiến: 6 sprint, mỗi sprint khoảng 2 tuần, nhóm 3 người
>
> Kế hoạch đang thực hiện: [Sprint 1 — Hoàn thiện thu chi local](SPRINT_1_PLAN.md)

## 1. Mục tiêu sản phẩm

SpendWise được phát triển thành ứng dụng quản lý tài chính cá nhân theo hướng offline-first dành cho người Việt. Phiên bản mục tiêu cần giúp người dùng:

1. Quản lý khoản thu, khoản chi và chuyển tiền giữa nhiều ví.
2. Theo dõi ngân sách tổng và ngân sách theo danh mục.
3. Hiểu dòng tiền qua dashboard, số liệu tổng hợp và biểu đồ.
4. Quét hóa đơn để giảm thời gian nhập giao dịch.
5. Tiếp tục sử dụng khi mất mạng và đồng bộ an toàn khi đăng nhập.
6. Chủ động sở hữu dữ liệu thông qua chức năng export.

Điểm khác biệt ưu tiên là OCR hóa đơn Việt Nam trên thiết bị kết hợp kiến trúc offline-first. Nhóm không cố gắng sao chép toàn bộ tính năng của các ứng dụng tài chính lớn trong phiên bản đầu tiên.

## 2. Hiện trạng sản phẩm

### Đã hoạt động

- Project Android dùng Kotlin, Jetpack Compose, Material 3, Navigation và Hilt.
- Room database cho giao dịch và ngân sách tháng.
- Chế độ local bằng người dùng tạm `local-demo-user`.
- Thêm khoản chi thủ công.
- Xem danh sách giao dịch.
- Đặt hoặc cập nhật ngân sách tháng hiện tại.
- Dashboard hiển thị tổng chi, số còn lại và trạng thái vượt ngân sách.
- Model đã có `syncState`, soft delete và các trường dành cho ảnh/OCR.
- Build debug và unit test hiện tại chạy thành công.

### Chưa hoàn thiện

- Xem chi tiết, sửa, xóa và hoàn tác giao dịch trên UI.
- Khoản thu, chuyển tiền và chọn ngày giờ giao dịch.
- Ví, danh mục tùy chỉnh, tìm kiếm, lọc và sắp xếp.
- Ngân sách theo danh mục, biểu đồ và cảnh báo.
- CameraX, ML Kit, receipt parser và màn hình xác nhận OCR.
- Firebase Authentication, Firestore và WorkManager đồng bộ.
- Giao dịch định kỳ, export, biometric và bộ test đầy đủ.

## 3. Phạm vi phiên bản mục tiêu

### Bắt buộc

- CRUD giao dịch đầy đủ và hoạt động offline.
- Khoản thu, khoản chi và chuyển tiền.
- Nhiều ví và danh mục tùy chỉnh.
- Tìm kiếm, lọc, sắp xếp và tổng hợp giao dịch.
- Ngân sách tổng và ngân sách theo danh mục.
- Dashboard thu, chi, số dư, tài sản và biểu đồ.
- Cảnh báo ngân sách ở ngưỡng 80% và 100%.
- Quét hóa đơn end-to-end và luôn có bước xác nhận.
- Firebase Authentication và đồng bộ offline-first.
- Giao dịch định kỳ.
- Export CSV.
- Migration, kiểm thử và xử lý trạng thái lỗi.

### Có thể làm nếu còn thời gian

- Ví gia đình hoặc chia sẻ.
- PIN hoặc biometric.
- Mục tiêu tiết kiệm.
- Import CSV.
- Dark mode tùy chọn.
- Dự báo chi tiêu cuối tháng.

### Ngoài phạm vi phiên bản đầu

- Liên kết ngân hàng thật.
- Quản lý đầu tư, vàng hoặc chứng khoán.
- Thuế thu nhập cá nhân.
- AI chatbot tài chính.
- Quản lý vay/nợ phức tạp.
- Đa tiền tệ và tỷ giá trực tuyến.

## 4. Nguyên tắc triển khai

- Room là nguồn dữ liệu chính mà UI quan sát.
- Tính năng thuộc luồng local phải dùng được khi không có mạng.
- OCR chỉ tạo bản nháp; người dùng phải xác nhận trước khi lưu giao dịch.
- Đồng bộ phải idempotent, có retry và không tạo bản ghi trùng.
- Thay đổi schema phải có migration và migration test.
- Mỗi issue là một lát cắt hoàn chỉnh gồm phần dữ liệu, nghiệp vụ, UI và test cần thiết.
- Issue không gắn cố định với một thành viên; mọi người tự nhận theo năng lực và tải công việc.

## 5. Thứ tự phát triển

```text
CRUD thu chi local ổn định
    ↓
Ví + danh mục + bộ lọc + ngân sách
    ↓
Dashboard nâng cao + OCR hóa đơn
    ↓
Tài khoản + đồng bộ cloud
    ↓
Giao dịch định kỳ + export
    ↓
Hardening + phát hành
```

Không chốt Firestore schema trước khi model giao dịch, ví và danh mục đã ổn định. Không đưa OCR tự động lưu thẳng vào database khi chưa có màn hình xác nhận.

## 6. Roadmap 6 sprint

### Sprint 1 — Hoàn thiện thu chi local

**Mục tiêu:** Biến bản hiện tại thành sổ thu chi local có thể sử dụng ổn định.

**Phạm vi:**

- Xem chi tiết và sửa giao dịch.
- Soft delete, xác nhận xóa và hoàn tác.
- Thêm khoản thu bên cạnh khoản chi.
- Chọn ngày giờ giao dịch.
- Chống lưu trùng và hoàn thiện trạng thái lưu.
- Lưu Room schema version 1 và tăng độ phủ test dữ liệu.
- Dashboard có tổng thu, tổng chi và số dư tháng.
- Xử lý đúng chuyển tháng và múi giờ.

**Điều kiện kết thúc:**

- CRUD thu/chi hoạt động ở airplane mode.
- Xóa không làm mất khả năng đồng bộ về sau.
- Dashboard cập nhật sau mọi thao tác giao dịch.
- Không tạo trùng do người dùng bấm lưu nhiều lần.
- Schema và các test liên quan được lưu trong repository.

Chi tiết và liên kết issue: [SPRINT_1_PLAN.md](SPRINT_1_PLAN.md).

### Sprint 2 — Ví, danh mục, bộ lọc và ngân sách

**Mục tiêu:** Đưa SpendWise từ danh sách thu chi đơn giản thành sổ tài chính có cấu trúc.

**Phạm vi:**

- Danh sách, tạo, sửa và ẩn ví.
- Danh mục mặc định và danh mục tùy chỉnh.
- Chọn ví và danh mục trong form giao dịch.
- Tìm kiếm theo cửa hàng hoặc ghi chú.
- Lọc theo thời gian, loại, ví và danh mục.
- Sắp xếp theo thời gian hoặc số tiền.
- Chuyển tiền giữa hai ví mà không làm tăng tổng thu/chi.
- Ngân sách tổng và ngân sách theo danh mục.
- Cảnh báo ngân sách ở ngưỡng 80% và 100%, không gửi trùng.
- Migration từ schema Sprint 1 và kiểm thử toàn vẹn số dư.

**Điều kiện kết thúc:**

- Số dư từng ví và tổng tài sản chính xác.
- Chuyển tiền không làm thay đổi tổng tài sản.
- Danh mục tùy chỉnh dùng được end-to-end.
- Bộ lọc và số tổng dùng cùng tập dữ liệu.
- Giao dịch mới cập nhật đúng mọi ngân sách liên quan.

### Sprint 3 — Dashboard nâng cao và OCR hóa đơn

**Mục tiêu:** Giảm thời gian nhập liệu bằng hóa đơn và bổ sung báo cáo có giá trị sử dụng.

**Phạm vi:**

- Biểu đồ chi tiêu theo danh mục.
- Xu hướng thu chi sáu tháng và so sánh tháng trước.
- Chọn ảnh từ camera hoặc thư viện.
- CameraX preview và capture.
- ML Kit Text Recognition.
- Parser cửa hàng, ngày mua và tổng tiền cho hóa đơn Việt Nam.
- Confidence cho từng trường và gợi ý danh mục theo từ khóa.
- Màn hình xem lại, chỉnh sửa và xác nhận kết quả OCR.
- Lưu ảnh trong app-specific storage và quản lý vòng đời ảnh.
- Nhập tay thay thế khi OCR thất bại.

**Điều kiện kết thúc:**

- Luồng `ảnh → OCR → xác nhận → giao dịch` chạy end-to-end.
- Không tự lưu kết quả khi người dùng chưa xác nhận.
- Có ít nhất 30 hóa đơn Việt Nam trong tập kiểm thử.
- Mục tiêu tham khảo: tổng tiền đúng ít nhất 90%, ngày 85%, cửa hàng 80%.
- OCR thất bại không chặn luồng nhập giao dịch thủ công.

### Sprint 4 — Tài khoản và đồng bộ cloud

**Mục tiêu:** Cho phép sử dụng nhiều thiết bị mà vẫn giữ nguyên khả năng offline-first.

**Phạm vi:**

- Đăng ký, đăng nhập, quên mật khẩu, profile và đăng xuất.
- Thay người dùng demo bằng authenticated user context.
- Lựa chọn nhập dữ liệu local vào tài khoản hoặc giữ tách biệt.
- Firestore schema và Security Rules.
- Security Rules test ngăn truy cập chéo người dùng.
- WorkManager upload/download queue.
- Upload pending upsert và pending delete.
- Download thay đổi từ cloud vào Room.
- Retry, idempotency, soft delete và conflict resolution bằng `updatedAt`.
- Trạng thái đồng bộ và lỗi đồng bộ trên UI.

**Điều kiện kết thúc:**

- Người dùng A không đọc hoặc ghi được dữ liệu của người dùng B.
- Ghi offline rồi online lại không mất hoặc nhân đôi dữ liệu.
- Thiết bị thứ hai nhận được dữ liệu sau khi đăng nhập.
- UI chỉ đọc Room; Firestore không cấp dữ liệu trực tiếp cho màn hình.

### Sprint 5 — Tự động hóa và quyền sở hữu dữ liệu

**Mục tiêu:** Giảm thao tác lặp lại và cho phép người dùng mang dữ liệu ra khỏi ứng dụng.

**Phạm vi:**

- Tạo và quản lý giao dịch định kỳ.
- Worker sinh giao dịch đến hạn theo cơ chế idempotent.
- Export CSV theo khoảng ngày, ví, loại và danh mục.
- Kiểm tra escaping, Unicode tiếng Việt và khả năng mở bằng Excel/Google Sheets.
- Cài đặt notification và quyền riêng tư.
- Luồng xóa tài khoản, dữ liệu local và ảnh nhạy cảm an toàn.
- Dự báo chi tiêu cuối tháng và số tiền có thể chi mỗi ngày nếu còn thời gian.

**Điều kiện kết thúc:**

- Worker chạy lại không sinh giao dịch trùng.
- CSV khớp dữ liệu và bộ lọc trong Room.
- Luồng xóa dữ liệu có xác nhận và không để lại ảnh nhạy cảm.

### Sprint 6 — Hardening và phát hành

**Mục tiêu:** Ổn định sản phẩm, hoàn thiện trải nghiệm và chuẩn bị bản phát hành/demo.

**Phạm vi:**

- Chuyển text hard-code sang string resources.
- Accessibility, content description, font scaling và màn hình nhỏ.
- Hoàn thiện empty/loading/error state.
- Chạy migration, sync, Security Rules và recovery test.
- Kiểm tra query/index với dữ liệu lớn.
- OCR regression với ảnh mờ, chói và nghiêng.
- Smoke test trên thiết bị thật và nhiều API Android.
- Chuẩn bị release build, dữ liệu demo, video và tài liệu kiến trúc.

**Điều kiện kết thúc:**

- Không còn bug P0/P1.
- CRUD hoạt động ổn định ở airplane mode.
- Không tạo trùng trong sync hoặc recurring worker.
- APK release được tạo thành công.
- README, kiến trúc và mô hình dữ liệu phản ánh đúng sản phẩm.

## 7. Cách phối hợp nhóm 3 người

- Tối đa ba issue ở trạng thái đang làm cùng lúc, mỗi người nhận một issue.
- Ưu tiên PR nhỏ, có thể review và merge độc lập.
- Không gắn nhãn theo tên hoặc số thứ tự thành viên.
- Nếu cùng sửa navigation, domain contract hoặc Room schema, phải thống nhất contract trước khi code.
- Khi hoàn tất issue, người khác review trước khi merge.
- Chỉ tạo issue cho sprint sắp thực hiện; backlog dài hạn được giữ trong tài liệu này.

Các file/contract có nguy cơ xung đột cao:

- Domain model và repository interface.
- Room database version, schema và migration.
- Firestore document schema và Security Rules.
- Navigation routes.
- Version catalog và plugin build.

## 8. Definition of Done

Một issue chỉ được đóng khi:

- Chức năng dùng nghiệp vụ và dữ liệu thật, không phải placeholder.
- Có loading, empty, success và error state phù hợp.
- Hoạt động offline nếu thuộc luồng local.
- Có validation và chống thao tác lặp nếu có nhập liệu.
- Business logic và truy vấn mới có test.
- Thay đổi DAO/schema có database hoặc migration test phù hợp.
- Không làm mất dữ liệu phiên bản trước.
- Build debug và các test liên quan chạy thành công.
- PR mô tả cách kiểm thử và có ít nhất một người khác review.
- Tài liệu/checklist được cập nhật trong cùng PR.

## 9. Chỉ số nghiệm thu phiên bản

- 100% luồng CRUD chính hoạt động ở airplane mode.
- Không tạo bản ghi trùng trong retry, sync hoặc recurring worker.
- Security Rules chặn truy cập chéo người dùng.
- OCR được đánh giá trên ít nhất 30 hóa đơn và có kết quả theo từng trường.
- Không có crash P0 trong kịch bản demo.
- Các phép tính thu, chi, số dư, chuyển tiền và ngân sách đều có test.
- Export CSV đối chiếu đúng với dữ liệu Room.

## 10. Rủi ro cần theo dõi

| Rủi ro | Biện pháp |
|---|---|
| Schema thay đổi liên tục | Chốt transaction/wallet/category trước khi chốt Firestore |
| OCR không chính xác | Confidence, màn hình xác nhận, tập dữ liệu thật và nhập tay thay thế |
| Sync làm mất hoặc nhân đôi dữ liệu | Room là nguồn chính, idempotency, retry và integration test |
| Ba người cùng sửa file lõi | Issue theo lát cắt, thống nhất contract sớm và PR nhỏ |
| Phạm vi quá lớn | Chỉ tạo issue cho sprint hiện tại và giữ rõ phần ngoài phạm vi |
| Thiếu thời gian kiểm thử | Không cắt test dữ liệu, migration hoặc Security Rules để chạy theo tính năng |

## 11. Quản lý kế hoạch trên GitHub

- GitHub chỉ chứa issue của sprint hiện tại để backlog không bị quá tải.
- Mỗi issue có mã dạng `S1-01`, `S2-01` để theo dõi nhưng không biểu thị người thực hiện.
- Issue sẵn sàng làm có nhãn `sẵn sàng nhận` và không được gán sẵn.
- Khi bắt đầu sprint mới, tạo tài liệu chi tiết riêng và các issue từ phạm vi trong roadmap này.
- Không xóa roadmap tổng khi đóng sprint; chỉ cập nhật kết quả và điều chỉnh phạm vi nếu có quyết định mới.
