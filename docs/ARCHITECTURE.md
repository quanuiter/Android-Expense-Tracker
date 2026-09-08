# Kiến trúc ban đầu

## 1. Mục tiêu thiết kế

Sườn được tối ưu cho ba điều: chạy tốt khi mất mạng, đủ đơn giản cho nhóm ba người và cho phép thêm Firebase/OCR mà không viết lại UI hoặc database.

```text
Compose Screen
      │ events / UI state
      ▼
   ViewModel
      │ domain model + Flow
      ▼
  Repository
      │
      ├──────────────► Room DAO ──► SQLite
      │                   ▲
      │                   │ single source of truth
      │
      └── WorkManager ──► Firestore (giai đoạn sau)

CameraX ──► ML Kit ──► ReceiptTextParser ──► màn hình xác nhận ──► Repository
```

## 2. Các lớp

### UI

Mỗi feature giữ screen và ViewModel của nó:

- `feature/auth`: trạng thái đăng nhập và form xác thực.
- `feature/dashboard`: tổng hợp số đã chi và ngân sách.
- `feature/transaction`: danh sách, tạo, sửa và xóa giao dịch.
- `feature/receipt`: camera, OCR, parser và xác nhận.
- `feature/budget`: thiết lập hạn mức và trạng thái cảnh báo.
- `feature/profile`: tài khoản, cài đặt, export và đăng xuất.

Composable chỉ nhận state và phát event. Truy vấn database, OCR parsing và Firebase không đặt trong Composable.

### ViewModel

ViewModel phối hợp repository và chuyển dữ liệu thành UI state. Luồng Room được chuyển thành `StateFlow` bằng `stateIn`, sau đó Compose quan sát bằng `collectAsStateWithLifecycle`.

### Repository

Repository là API nghiệp vụ của data layer. Implementation hiện tại có tiền tố `OfflineFirst` và ghi Room trước. Khi Firestore được thêm, UI/ViewModel không đổi; repository sẽ đánh dấu bản ghi cần đồng bộ và xếp WorkManager.

### Local database

Room là single source of truth. Điều này có nghĩa:

1. UI không đọc Firestore trực tiếp.
2. Thêm/sửa/xóa thành công ngay sau khi ghi Room.
3. Cloud sync chỉ thay đổi dữ liệu Room hoặc trạng thái đồng bộ.
4. Mất mạng không làm gián đoạn CRUD và dashboard.

## 3. Chiến lược đồng bộ dự kiến

Mỗi bản ghi có `syncState`:

- `SYNCED`: local trùng với server.
- `PENDING_UPSERT`: cần tạo/cập nhật trên Firestore.
- `PENDING_DELETE`: cần xóa/đánh dấu xóa trên Firestore.
- `FAILED`: lần đồng bộ gần nhất lỗi và cần retry.

Luồng ghi:

```text
User lưu giao dịch
→ Room upsert với PENDING_UPSERT
→ UI cập nhật ngay
→ enqueue unique WorkManager job
→ worker gửi Firestore khi có mạng
→ Room đổi thành SYNCED
```

Xung đột MVP dùng `updatedAt` và last-write-wins. Nếu hai thiết bị sửa cùng bản ghi, bản có `updatedAt` mới hơn thắng. Đồng hồ client có thể sai, nên nếu triển khai sâu hơn cần thêm server timestamp và revision.

## 4. Ranh giới OCR

OCR chia thành ba trách nhiệm độc lập:

1. `ReceiptCapture`: CameraX tạo ảnh rõ, đúng chiều.
2. `TextRecognizer`: adapter gọi ML Kit và trả raw text.
3. `ReceiptTextParser`: Kotlin thuần trích merchant, ngày, tổng tiền.

ML Kit không tự hiểu trường “tổng tiền”. Nó chỉ trả text và vị trí; parser mới quyết định dòng nào là tổng. `ReceiptDraft` luôn phải qua màn hình xác nhận trước khi tạo `Transaction`.

## 5. Dependency injection

Hilt hiện cấp:

- một `SpendWiseDatabase` cho toàn ứng dụng;
- các DAO;
- `TransactionRepository` và `BudgetRepository`;
- `Clock` để code nghiệp vụ có thể test được.

Firebase adapters, OCR adapter và notification manager sẽ được bind khi chúng có implementation thật.

## 6. Vì sao chưa tách nhiều Gradle module

Nhóm chỉ có ba người và domain chưa ổn định. Một module với package theo feature giúp build nhanh, refactor dễ và vẫn chia ownership rõ. Chỉ nên tách module khi có một trong các dấu hiệu:

- build time trở thành vấn đề;
- feature cần tái sử dụng độc lập;
- boundary liên tục bị vi phạm;
- nhóm thường xuyên conflict cùng file build.

