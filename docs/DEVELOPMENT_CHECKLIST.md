# Checklist Sprint 1 — Hoàn thiện thu chi local

> Kế hoạch chi tiết: [SPRINT_1_PLAN.md](SPRINT_1_PLAN.md)
>
> Cập nhật gần nhất: 12/09/2026

## Cách sử dụng

- Chọn một issue có nhãn `sẵn sàng nhận`, tự gán người thực hiện và chuyển sang trạng thái đang làm.
- Mỗi issue phải được triển khai end-to-end trong phạm vi mô tả; không chỉ làm riêng UI hoặc data.
- Chỉ đánh dấu `[x]` khi toàn bộ tiêu chí nghiệm thu và Definition of Done đã đạt.
- Đính kèm `Closes #...` trong PR để GitHub tự đóng issue sau khi merge.
- Cập nhật checklist này trong cùng PR với chức năng.

## Baseline đã có

- [x] Project Kotlin + Compose + Material 3.
- [x] Navigation và năm tab chính.
- [x] Hilt dependency injection.
- [x] Room database version 1.
- [x] Thêm khoản chi thủ công vào Room.
- [x] Danh sách giao dịch đọc dữ liệu Room.
- [x] Đặt/cập nhật ngân sách tháng hiện tại.
- [x] Dashboard có tổng chi và số còn lại.
- [x] Build debug và unit test hiện tại thành công.

## Issue có thể claim

- [ ] [S1-01 — Xem chi tiết giao dịch](https://github.com/quanuiter/Android-Expense-Tracker/issues/24)
  - [ ] Mở chi tiết từ danh sách bằng `transactionId`.
  - [ ] Đọc đúng bản ghi theo `id + userId`.
  - [ ] Hiển thị đủ thông tin và các trạng thái màn hình.
  - [ ] Có test DAO/repository/ViewModel liên quan.

- [ ] [S1-02 — Sửa giao dịch](https://github.com/quanuiter/Android-Expense-Tracker/issues/25)
  - [ ] Form được điền sẵn từ dữ liệu hiện tại.
  - [ ] Validation và lưu cập nhật hoạt động.
  - [ ] `updatedAt`/trạng thái chờ đồng bộ được cập nhật.
  - [ ] Danh sách và dashboard phản ánh dữ liệu mới.
  - [ ] Có test luồng thành công và lỗi.

- [ ] [S1-03 — Xóa và hoàn tác giao dịch](https://github.com/quanuiter/Android-Expense-Tracker/issues/26)
  - [ ] Có xác nhận trước khi xóa.
  - [ ] Soft delete kiểm tra `id + userId`.
  - [ ] Snackbar Hoàn tác khôi phục giao dịch.
  - [ ] Danh sách/dashboard cập nhật đúng.
  - [ ] Có test delete, restore và isolation.

- [ ] [S1-04 — Thêm và hiển thị khoản thu](https://github.com/quanuiter/Android-Expense-Tracker/issues/27)
  - [ ] Form chọn được khoản thu hoặc khoản chi.
  - [ ] Room lưu đúng `TransactionType`.
  - [ ] Danh sách/chi tiết phân biệt rõ hai loại.
  - [ ] Có test lưu và mapping hiển thị.

- [ ] [S1-05 — Chọn ngày giờ giao dịch](https://github.com/quanuiter/Android-Expense-Tracker/issues/28)
  - [ ] Có date picker và time picker.
  - [ ] State giữ đúng ngày giờ đã chọn.
  - [ ] Lưu/hiển thị đúng theo múi giờ thiết bị.
  - [ ] Có test biên tháng và timezone.

- [ ] [S1-06 — Chống lưu trùng và hiển thị trạng thái lưu](https://github.com/quanuiter/Android-Expense-Tracker/issues/29)
  - [ ] Nút lưu bị khóa trong lúc đang xử lý.
  - [ ] Double-submit chỉ tạo một bản ghi.
  - [ ] Có trạng thái đang lưu, thành công và lỗi.
  - [ ] Lỗi không làm mất dữ liệu form và có thể thử lại.
  - [ ] Có test submit lặp và lỗi repository.

- [ ] [S1-07 — Lưu schema Room và bổ sung kiểm thử dữ liệu](https://github.com/quanuiter/Android-Expense-Tracker/issues/30)
  - [ ] Room schema version 1 được export và commit.
  - [ ] Có test converter và mapper.
  - [ ] Có test DAO CRUD, soft delete/restore và `userId`.
  - [ ] Có test repository cho các thao tác chính.

- [ ] [S1-08 — Hiển thị tổng thu, tổng chi và số dư tháng](https://github.com/quanuiter/Android-Expense-Tracker/issues/31)
  - [ ] DAO/repository cung cấp đủ ba số tổng theo tháng.
  - [ ] Dashboard có thẻ tổng thu, tổng chi và số dư.
  - [ ] Bỏ qua giao dịch đã xóa.
  - [ ] Số liệu tự cập nhật sau CRUD/restore.
  - [ ] Có test tính tổng và dữ liệu rỗng.

- [ ] [S1-09 — Tự cập nhật tháng và xử lý múi giờ](https://github.com/quanuiter/Android-Expense-Tracker/issues/32)
  - [ ] Logic tháng dùng `Clock`/`ZoneId` có thể inject.
  - [ ] Dashboard làm mới khi app trở lại foreground hoặc sang ngày mới.
  - [ ] Không thiếu/trùng dữ liệu ở biên tháng.
  - [ ] Có test nhiều múi giờ và thời điểm chuyển tháng.

## Nghiệm thu Sprint 1

- [ ] CRUD thu/chi hoạt động ở airplane mode.
- [ ] Chọn được loại, ngày và giờ giao dịch.
- [ ] Xóa không làm mất dữ liệu vật lý và có thể hoàn tác.
- [ ] Dashboard cập nhật đúng sau create/update/delete/restore.
- [ ] Không tạo giao dịch trùng do thao tác UI.
- [ ] Room schema version 1 có trong version control.
- [ ] `assembleDebug` và toàn bộ test liên quan thành công.

## Theo dõi tiến độ

| Mã | Issue | Trạng thái | Người nhận | PR | Ghi chú |
|---|---:|---|---|---|---|
| S1-01 | #24 | Chưa nhận | | | |
| S1-02 | #25 | Chưa nhận | | | |
| S1-03 | #26 | Chưa nhận | | | |
| S1-04 | #27 | Chưa nhận | | | |
| S1-05 | #28 | Chưa nhận | | | |
| S1-06 | #29 | Chưa nhận | | | |
| S1-07 | #30 | Chưa nhận | | | |
| S1-08 | #31 | Chưa nhận | | | |
| S1-09 | #32 | Chưa nhận | | | |
