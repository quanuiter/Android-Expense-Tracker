# Kế hoạch phát triển SpendWise

> Cập nhật: 12/09/2026
>
> Giai đoạn hiện tại: Sprint 1 — Hoàn thiện thu chi local

## 1. Mục tiêu Sprint 1

Sprint 1 biến bản hiện tại từ một ứng dụng nhập khoản chi cơ bản thành sổ thu chi local có thể sử dụng được. Khi kết thúc sprint, người dùng phải có thể:

- Thêm khoản thu và khoản chi.
- Xem chi tiết, sửa, xóa và hoàn tác giao dịch.
- Chọn chính xác ngày, giờ của giao dịch.
- Thấy tổng thu, tổng chi và số dư của tháng hiện tại.
- Dùng toàn bộ luồng trên khi không có mạng.

## 2. Hiện trạng làm nền

- Project Android dùng Kotlin, Jetpack Compose, Material 3, Navigation và Hilt.
- Room đang lưu giao dịch và ngân sách tháng.
- Có chế độ local với người dùng tạm `local-demo-user`.
- Đã thêm khoản chi thủ công và hiển thị danh sách giao dịch.
- Dashboard đã có tổng chi, số còn lại và trạng thái ngân sách.
- Model đã có `syncState`, soft delete và các trường dành cho ảnh/OCR.
- Build debug và unit test hiện tại chạy thành công.

## 3. Cách tổ chức công việc

Sprint 1 có đúng 9 issue có thể nhận độc lập. Mỗi issue là một lát cắt hoàn chỉnh: người nhận tự làm các thay đổi cần thiết từ Room/repository đến ViewModel/UI và kiểm thử trong phạm vi chức năng đó.

- Không gắn issue cố định cho thành viên.
- Thành viên tự claim issue bằng cách tự gán mình trên GitHub.
- Không chia theo tầng UI, data hoặc analytics vì cách đó khiến một issue phải chờ issue khác.
- Mã `S1-01` đến `S1-09` là thứ tự quản lý, không phải quan hệ phụ thuộc bắt buộc.
- Nếu hai issue cùng chạm một contract dùng chung, người làm thống nhất trong PR trước khi merge.

## 4. Các chức năng cần hoàn thiện

### S1-01 — Xem chi tiết giao dịch

**Kết quả người dùng nhận được:** Chạm vào một giao dịch trong danh sách sẽ mở màn hình hiển thị đầy đủ số tiền, loại giao dịch, danh mục, ghi chú và thời gian.

**Code cần phát triển:**

- DAO/repository đọc một giao dịch theo cả `id` và `userId`.
- ViewModel nạp dữ liệu và biểu diễn trạng thái đang tải, thành công, không tìm thấy và lỗi.
- Route điều hướng có `transactionId` và màn hình chi tiết Compose.
- Unit test cho truy vấn, mapping và trạng thái ViewModel.

**Hoàn thành khi:** Mở đúng giao dịch của người dùng hiện tại, không hiển thị dữ liệu đã xóa hoặc của người dùng khác, và xử lý được mọi trạng thái màn hình.

### S1-02 — Sửa giao dịch

**Kết quả người dùng nhận được:** Có thể mở giao dịch đã lưu, thay đổi thông tin hợp lệ và thấy dữ liệu mới ngay khi quay lại danh sách/dashboard.

**Code cần phát triển:**

- Repository cập nhật giao dịch có kiểm tra `id + userId`.
- Form sửa được điền sẵn dữ liệu hiện tại và dùng lại validation của form giao dịch.
- ViewModel quản lý tải dữ liệu, lưu, thành công và lỗi.
- Cập nhật `updatedAt` và trạng thái chờ đồng bộ khi lưu.
- Test cập nhật thành công, dữ liệu không hợp lệ và giao dịch không thuộc người dùng.

**Hoàn thành khi:** Dữ liệu Room thay đổi đúng một bản ghi, UI phản ánh kết quả mới và lỗi không làm mất dữ liệu đang nhập.

### S1-03 — Xóa và hoàn tác giao dịch

**Kết quả người dùng nhận được:** Có thể xác nhận xóa giao dịch và bấm Hoàn tác trong Snackbar để khôi phục.

**Code cần phát triển:**

- Soft delete và restore trong DAO/repository, đều kiểm tra `id + userId`.
- Hộp thoại xác nhận xóa và Snackbar có hành động Hoàn tác.
- ViewModel xử lý đang xóa, xóa thành công, hoàn tác và lỗi.
- Test bản ghi bị ẩn sau xóa, xuất hiện lại sau hoàn tác và không xóa nhầm dữ liệu.

**Hoàn thành khi:** Xóa không loại bỏ vật lý bản ghi, danh sách/dashboard cập nhật đúng và hoàn tác khôi phục đủ dữ liệu.

### S1-04 — Thêm và hiển thị khoản thu

**Kết quả người dùng nhận được:** Form cho chọn khoản thu hoặc khoản chi; danh sách phân biệt rõ hai loại và hiển thị dấu/màu đúng.

**Code cần phát triển:**

- Bộ chọn `INCOME`/`EXPENSE` trong form và state của ViewModel.
- Lưu đúng `TransactionType` qua repository.
- Trình bày khoản thu và khoản chi nhất quán trong danh sách/chi tiết.
- Test lưu hai loại giao dịch và mapping hiển thị.

**Hoàn thành khi:** Người dùng thêm được cả thu lẫn chi offline, dữ liệu lưu đúng loại và UI không nhầm dấu số tiền.

### S1-05 — Chọn ngày giờ giao dịch

**Kết quả người dùng nhận được:** Có thể chọn ngày và giờ thực tế thay vì mọi giao dịch luôn dùng thời điểm hiện tại.

**Code cần phát triển:**

- Date picker, time picker và state cho thời điểm đang chọn.
- Chuyển đổi `LocalDateTime` sang thời điểm lưu trữ bằng múi giờ thiết bị.
- Hiển thị lại ngày giờ đã chọn trong form, danh sách và chi tiết.
- Test ngày thường, biên tháng và chuyển đổi múi giờ.

**Hoàn thành khi:** Thời điểm lưu và hiển thị khớp lựa chọn của người dùng, kể cả khi giao dịch thuộc tháng khác.

### S1-06 — Chống lưu trùng và hiển thị trạng thái lưu

**Kết quả người dùng nhận được:** Nút lưu chỉ tạo một giao dịch dù bấm nhanh nhiều lần; màn hình báo rõ đang lưu, thành công hoặc thất bại.

**Code cần phát triển:**

- Trạng thái `isSaving`, success/error event và vô hiệu hóa submit khi đang xử lý.
- Bảo vệ ViewModel/repository trước nhiều yêu cầu lưu đồng thời.
- Giữ lại dữ liệu form khi lưu lỗi và cho phép thử lại.
- Test double-submit, lưu thành công và lỗi repository.

**Hoàn thành khi:** Một thao tác lưu chỉ tạo một bản ghi, không điều hướng lặp và lỗi có thể phục hồi.

### S1-07 — Lưu schema Room và bổ sung kiểm thử dữ liệu

**Kết quả kỹ thuật:** Schema database được lưu trong version control và các contract dữ liệu quan trọng có kiểm thử để tránh regression khi các chức năng khác thay đổi Room.

**Code cần phát triển:**

- Bật Room schema export và commit schema version 1.
- Test converter, mapper, DAO CRUD, soft delete/restore và isolation theo `userId`.
- Test repository cho các thao tác đọc, thêm, sửa, xóa và khôi phục.
- Cập nhật cấu hình test cần thiết cho Room.

**Hoàn thành khi:** Schema có thể dùng cho migration test về sau và toàn bộ test dữ liệu chạy ổn định, độc lập.

### S1-08 — Hiển thị tổng thu, tổng chi và số dư tháng

**Kết quả người dùng nhận được:** Dashboard hiển thị ba số riêng biệt của tháng: tổng thu, tổng chi và số dư `thu - chi`; số liệu tự đổi sau thao tác giao dịch.

**Code cần phát triển:**

- DAO query tổng tiền theo `userId`, loại giao dịch và khoảng thời gian, bỏ qua bản ghi đã xóa.
- Repository cung cấp các `Flow` tổng hợp.
- Dashboard ViewModel kết hợp dữ liệu thành UI state.
- Ba thẻ số liệu Compose và empty/error state phù hợp.
- Test phép tính với thu, chi, dữ liệu rỗng và bản ghi đã xóa.

**Hoàn thành khi:** Ba số khớp dữ liệu Room của tháng đang xem và cập nhật phản ứng sau create/update/delete/restore.

### S1-09 — Tự cập nhật tháng và xử lý múi giờ

**Kết quả người dùng nhận được:** Khi sang tháng mới hoặc thay đổi múi giờ, dashboard dùng đúng khoảng thời gian mà không cần khởi động lại ứng dụng.

**Code cần phát triển:**

- Tách logic tính đầu/cuối tháng và cho phép inject `Clock`/`ZoneId` để kiểm thử.
- Cơ chế làm mới khoảng tháng khi app quay lại foreground hoặc ngày hệ thống thay đổi.
- Kết nối lại các `Flow` dashboard với khoảng tháng mới.
- Test giao dịch tại biên đầu/cuối tháng và các múi giờ khác nhau.

**Hoàn thành khi:** Không tính thiếu hoặc tính trùng giao dịch ở biên tháng và dashboard tự chuyển sang tháng mới.

## 5. Definition of Done cho mọi issue

Một issue chỉ được đóng khi:

- Chức năng dùng dữ liệu thật, không phải placeholder.
- Có loading, empty, success và error state phù hợp.
- Validation và chống thao tác lặp đã được xử lý nếu có nhập liệu.
- Business logic và truy vấn mới có test.
- Build debug và các test liên quan chạy thành công.
- Không làm hỏng dữ liệu hoặc chức năng đang có.
- PR mô tả cách kiểm thử và có ít nhất một người khác review.
- Checklist được cập nhật trong cùng PR.

## 6. Điều kiện kết thúc Sprint 1

- CRUD khoản thu/chi hoạt động khi bật airplane mode.
- Người dùng chọn được loại, ngày và giờ giao dịch.
- Xóa là soft delete và có thể hoàn tác.
- Dashboard cập nhật đúng sau thêm, sửa, xóa và hoàn tác.
- Không tạo giao dịch trùng do bấm lưu nhiều lần.
- Schema Room version 1 đã được lưu và các test liên quan đều qua.

## 7. Giai đoạn sau Sprint 1

Chỉ tạo issue cho giai đoạn sau khi Sprint 1 đã được nghiệm thu. Backlog định hướng gồm:

1. Ví, danh mục tùy chỉnh, tìm kiếm và bộ lọc.
2. Ngân sách theo danh mục, biểu đồ và cảnh báo.
3. Quét hóa đơn bằng CameraX/ML Kit và màn hình xác nhận OCR.
4. Firebase Authentication và đồng bộ offline-first.
5. Giao dịch định kỳ, export CSV và hoàn thiện phát hành.
