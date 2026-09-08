# SpendWise — Hướng dẫn xây dựng từ số 0

Tài liệu này giải thích SpendWise được hình thành theo thứ tự nào, bắt đầu từ file đầu tiên của project cho đến khi dữ liệu xuất hiện trên màn hình. Mục tiêu là để một thành viên chưa đọc code vẫn hiểu **tạo cái gì trước, tại sao phải tạo nó và bước sau dùng lại bước trước như thế nào**.

> Trạng thái được ghi nhận ngày 08/09/2026. Đây vừa là hướng dẫn học, vừa là checklist tiến độ của nhóm.

## 1. Cách đọc trạng thái

- `[x]`: đã có trong source hiện tại.
- `[~]`: đã có sườn hoặc contract, chưa hoàn thiện chức năng.
- `[ ]`: chưa triển khai.

## 2. Trước khi tạo project: phải chốt bài toán

Không nên mở Android Studio rồi tạo màn hình ngay. Đầu tiên phải viết ngắn gọn ứng dụng giải quyết vấn đề gì.

### 2.1. Bài toán

Người dùng cần:

1. Ghi lại thu/chi.
2. Chụp hóa đơn thay vì nhập toàn bộ bằng tay.
3. Xem tổng chi theo thời gian và danh mục.
4. Đặt ngân sách tháng.
5. Vẫn xem và nhập dữ liệu khi không có mạng.

### 2.2. Các use case MVP

- Đăng ký, đăng nhập.
- Thêm, xem, sửa, xóa giao dịch.
- Đặt ngân sách tháng.
- Quét hóa đơn và xác nhận kết quả OCR.
- Xem dashboard.
- Lưu local và đồng bộ khi có mạng.

### 2.3. Quyết định kỹ thuật quan trọng

- Android native bằng Kotlin.
- UI bằng Jetpack Compose.
- Room là nguồn dữ liệu chính.
- Firebase dùng cho Auth và đồng bộ, không phải nguồn đọc trực tiếp của UI.
- CameraX chụp ảnh, ML Kit nhận dạng text.
- Tiền lưu bằng `Long`, không dùng `Double`.

Sau khi các ý trên được thống nhất mới bắt đầu tạo project.

---

## 3. File đầu tiên là file nào?

Câu trả lời phụ thuộc vào đang nói về **quá trình build** hay **quá trình chạy app**.

### 3.1. Khi Gradle mở project

Thứ tự hiểu đơn giản:

```text
settings.gradle.kts
        ↓
build.gradle.kts ở root
        ↓
gradle/libs.versions.toml
        ↓
app/build.gradle.kts
        ↓
AndroidManifest.xml và source code
```

Vì vậy, nếu tạo thủ công từ thư mục rỗng thì file nên bắt đầu là [`settings.gradle.kts`](../settings.gradle.kts).

### 3.2. Khi Android chạy ứng dụng

Thứ tự runtime:

```text
AndroidManifest.xml
        ↓ khai báo Application và launcher Activity
SpendWiseApplication.kt
        ↓ khởi tạo Hilt cho toàn app
MainActivity.kt
        ↓ gọi setContent
SpendWiseTheme
        ↓
SpendWiseApp
        ↓
Navigation chọn Screen cần hiển thị
```

Do đó, `MainActivity.kt` không phải file đầu tiên Gradle đọc, nhưng là điểm bắt đầu giao diện của ứng dụng.

---

## 4. Giai đoạn A — tạo hệ thống build

### Bước A1 — tạo `settings.gradle.kts` `[x]`

File: [`settings.gradle.kts`](../settings.gradle.kts)

Nhiệm vụ:

- Đặt tên project là `SpendWise`.
- Khai báo nơi tải plugin/dependency: Google Maven và Maven Central.
- Khai báo module `:app`.

Nếu thiếu dòng `include(":app")`, Gradle không biết module Android tồn tại.

Kiểm tra sau bước này:

```powershell
.\gradlew.bat projects
```

Kết quả mong đợi phải có project `:app`.

### Bước A2 — tạo Version Catalog `[x]`

File: [`gradle/libs.versions.toml`](../gradle/libs.versions.toml)

Nhiệm vụ:

- Lưu phiên bản AGP, Kotlin, Compose, Room, Hilt, Firebase… tại một chỗ.
- Tạo alias như `libs.androidx.room.runtime` để file module dễ đọc.
- Tránh mỗi thành viên ghi một phiên bản khác nhau.

Ví dụ tư duy:

```text
room = 2.8.4
        ↓
room-runtime dùng phiên bản room
room-ktx dùng phiên bản room
room-compiler dùng phiên bản room
```

Khi nâng Room chỉ đổi một dòng, không sửa ba dependency riêng lẻ.

### Bước A3 — tạo `build.gradle.kts` ở root `[x]`

File: [`build.gradle.kts`](../build.gradle.kts)

Nhiệm vụ:

- Khai báo các Gradle plugin có thể được các module sử dụng.
- `apply false` nghĩa là chỉ đăng ký plugin ở root, chưa áp dụng vào root project.
- Plugin thực sự được áp dụng trong `app/build.gradle.kts`.

### Bước A4 — tạo Gradle Wrapper và properties `[x]`

Các file:

- [`gradlew`](../gradlew) và [`gradlew.bat`](../gradlew.bat): lệnh chạy Gradle trên macOS/Linux và Windows.
- [`gradle/wrapper/gradle-wrapper.properties`](../gradle/wrapper/gradle-wrapper.properties): chỉ định Gradle 8.13.
- [`gradle.properties`](../gradle.properties): cấu hình bộ nhớ, AndroidX và Kotlin style.

Wrapper giúp mọi thành viên dùng cùng một Gradle, không phụ thuộc Gradle cài toàn cục.

### Bước A5 — cấu hình module Android `[x]`

File: [`app/build.gradle.kts`](../app/build.gradle.kts)

Nhiệm vụ:

- Xác định `applicationId`: `com.spendwise.app`.
- `minSdk = 26`: Android thấp nhất có thể cài.
- `compileSdk/targetSdk = 36`.
- Java/Kotlin target 17.
- Bật Compose.
- Thêm Room, Hilt, Navigation, CameraX, ML Kit, Firebase và thư viện test.

Tại sao dependency được khai báo sớm nhưng chưa dùng hết? Vì đây là stack đã chốt. Tuy nhiên chỉ tạo implementation thật khi sprint tương ứng bắt đầu.

Điều kiện hoàn thành giai đoạn A:

- `gradlew help` chạy thành công.
- Dependency resolve không có artifact bị thiếu.
- Android Studio nhận module `app`.

**Tiến độ:** hoàn thành. Gradle DSL và runtime classpath đã được kiểm tra thành công.

---

## 5. Giai đoạn B — tạo điểm vào của ứng dụng

### Bước B1 — tạo `AndroidManifest.xml` `[x]`

File: [`app/src/main/AndroidManifest.xml`](../app/src/main/AndroidManifest.xml)

Manifest khai báo:

- tên `SpendWiseApplication`;
- `MainActivity` là launcher Activity;
- quyền camera, internet, trạng thái mạng và notification;
- icon, theme và tên ứng dụng.

Android đọc Manifest để biết phải mở class nào khi người dùng nhấn icon.

### Bước B2 — tạo Application class `[x]`

File: [`SpendWiseApplication.kt`](../app/src/main/java/com/spendwise/app/SpendWiseApplication.kt)

```kotlin
@HiltAndroidApp
class SpendWiseApplication : Application()
```

`@HiltAndroidApp` tạo dependency container ở cấp ứng dụng. Nếu chưa có class này, `@AndroidEntryPoint` và `@HiltViewModel` không hoạt động.

### Bước B3 — tạo `MainActivity` `[x]`

File: [`MainActivity.kt`](../app/src/main/java/com/spendwise/app/MainActivity.kt)

`MainActivity` làm ba việc:

1. Bật edge-to-edge.
2. Gọi `setContent` để bắt đầu Compose.
3. Bọc ứng dụng bằng `SpendWiseTheme`, sau đó gọi `SpendWiseApp`.

Không viết business logic, truy vấn Room hoặc gọi Firebase trong Activity.

### Bước B4 — tạo theme và resource cơ bản `[x]`

Các file:

- [`Color.kt`](../app/src/main/java/com/spendwise/app/core/designsystem/theme/Color.kt)
- [`Theme.kt`](../app/src/main/java/com/spendwise/app/core/designsystem/theme/Theme.kt)
- [`strings.xml`](../app/src/main/res/values/strings.xml)
- [`themes.xml`](../app/src/main/res/values/themes.xml)

Mục đích là để màu sắc và giao diện chung nằm một chỗ. Screen không tự hard-code bộ màu riêng.

Điều kiện hoàn thành giai đoạn B: app mở được một Compose content mà không crash do Hilt hoặc theme.

---

## 6. Giai đoạn C — dựng navigation và màn hình rỗng

Không nên làm chi tiết dashboard trước khi chưa di chuyển được giữa các feature.

### Bước C1 — định nghĩa route `[x]`

File: [`SpendWiseDestination.kt`](../app/src/main/java/com/spendwise/app/navigation/SpendWiseDestination.kt)

Các route ban đầu:

```text
auth
dashboard
transactions
transactions/add
receipt
budget
profile
```

Route là ID nội bộ, còn label là chữ hiển thị cho người dùng. Không dùng trực tiếp label tiếng Việt làm route.

### Bước C2 — tạo NavHost `[x]`

File: [`SpendWiseApp.kt`](../app/src/main/java/com/spendwise/app/navigation/SpendWiseApp.kt)

`SpendWiseApp` giữ `NavController`, bottom navigation và ánh xạ route sang screen.

Ví dụ:

```text
route transactions
        ↓
TransactionListScreen
        ↓ người dùng nhấn +
route transactions/add
        ↓
AddTransactionScreen
```

### Bước C3 — tạo screen placeholder `[x]`

Tạo screen cho từng feature trước, dù chức năng sâu chưa có:

- `AuthScreen`
- `DashboardScreen`
- `TransactionListScreen`
- `ReceiptCaptureScreen`
- `BudgetScreen`
- `ProfileScreen`

Lợi ích: nhóm thấy được toàn bộ luồng ứng dụng, thống nhất UX và có chỗ để từng thành viên phát triển tiếp.

---

## 7. Giai đoạn D — thiết kế model nghiệp vụ

Model nghiệp vụ nên được tạo **trước Room entity và trước UI form**, vì cả database lẫn UI đều phụ thuộc model.

### Bước D1 — danh mục `[x]`

File: [`Category.kt`](../app/src/main/java/com/spendwise/app/core/model/Category.kt)

Enum chứa mã ổn định như `FOOD`, `TRANSPORT` và tên hiển thị như “Ăn uống”, “Di chuyển”. Database lưu mã, UI hiển thị tên.

### Bước D2 — giao dịch `[x]`

File: [`Transaction.kt`](../app/src/main/java/com/spendwise/app/core/model/Transaction.kt)

Các quyết định cần hiểu:

- ID là UUID string để dùng chung local/cloud.
- `amountMinor` là `Long`; VND không có phần thập phân.
- `occurredAt` là thời điểm giao dịch.
- `deletedAt` phục vụ soft delete.
- `syncState` cho biết bản ghi đã lên cloud hay chưa.

### Bước D3 — ngân sách `[x]`

File: [`MonthlyBudget.kt`](../app/src/main/java/com/spendwise/app/core/model/MonthlyBudget.kt)

MVP chỉ có một hạn mức tổng cho mỗi tháng. Ngân sách từng category để sau nhằm tránh làm schema v1 quá phức tạp.

### Bước D4 — kết quả OCR tạm `[x]`

File: [`ReceiptDraft.kt`](../app/src/main/java/com/spendwise/app/core/model/ReceiptDraft.kt)

`ReceiptDraft` chưa phải giao dịch. Đây là dữ liệu ML Kit/parser dự đoán và người dùng còn phải sửa hoặc xác nhận.

---

## 8. Giai đoạn E — xây database Room từ dưới lên

Thứ tự đúng:

```text
Domain model
    ↓
Room Entity + TypeConverter
    ↓
DAO
    ↓
RoomDatabase
    ↓
Mapper
    ↓
Repository
```

### Bước E1 — tạo Entity `[x]`

Các file:

- [`TransactionEntity.kt`](../app/src/main/java/com/spendwise/app/core/database/entity/TransactionEntity.kt)
- [`MonthlyBudgetEntity.kt`](../app/src/main/java/com/spendwise/app/core/database/entity/MonthlyBudgetEntity.kt)

Entity mô tả bảng SQLite. Entity gần giống domain model nhưng không phải cùng một lớp, vì schema lưu trữ có thể thay đổi khác với dữ liệu UI cần.

### Bước E2 — tạo TypeConverter `[x]`

File: [`DatabaseConverters.kt`](../app/src/main/java/com/spendwise/app/core/database/DatabaseConverters.kt)

SQLite không hiểu trực tiếp `Instant`, `YearMonth` hoặc enum. Converter biến:

```text
Instant   ↔ Long milliseconds
YearMonth ↔ "2026-09"
Enum      ↔ String name
```

### Bước E3 — tạo DAO `[x]`

Các file:

- [`TransactionDao.kt`](../app/src/main/java/com/spendwise/app/core/database/dao/TransactionDao.kt)
- [`MonthlyBudgetDao.kt`](../app/src/main/java/com/spendwise/app/core/database/dao/MonthlyBudgetDao.kt)

DAO chứa SQL và là lớp duy nhất biết chi tiết truy vấn Room.

Ví dụ tổng chi tháng:

```text
lọc theo user
AND type = EXPENSE
AND chưa bị xóa
AND thời gian nằm trong tháng
→ SUM(amountMinor)
```

DAO trả `Flow` cho dữ liệu cần cập nhật UI tự động.

### Bước E4 — tạo database `[x]`

File: [`SpendWiseDatabase.kt`](../app/src/main/java/com/spendwise/app/core/database/SpendWiseDatabase.kt)

Database gom entity, converter và DAO. Version hiện tại là 1. Khi đổi schema phải tăng version và viết migration; không dùng destructive migration cho dữ liệu tài chính.

### Bước E5 — tạo mapper `[x]`

File: [`DatabaseMappers.kt`](../app/src/main/java/com/spendwise/app/core/database/mapper/DatabaseMappers.kt)

Mapper chuyển đổi:

```text
TransactionEntity ↔ Transaction
MonthlyBudgetEntity ↔ MonthlyBudget
```

Nhờ vậy ViewModel không phụ thuộc Room annotation hoặc cấu trúc bảng.

---

## 9. Giai đoạn F — Repository và Hilt

### Bước F1 — tạo repository interface `[x]`

Các file:

- [`TransactionRepository.kt`](../app/src/main/java/com/spendwise/app/core/data/TransactionRepository.kt)
- [`BudgetRepository.kt`](../app/src/main/java/com/spendwise/app/core/data/BudgetRepository.kt)

Interface nói ứng dụng **cần làm gì**, không nói Room hay Firebase thực hiện thế nào.

Ví dụ ViewModel chỉ biết:

```kotlin
repository.observeTransactions(userId)
repository.save(transaction)
```

### Bước F2 — implementation offline-first `[x]`

Các file:

- [`OfflineFirstTransactionRepository.kt`](../app/src/main/java/com/spendwise/app/core/data/OfflineFirstTransactionRepository.kt)
- [`OfflineFirstBudgetRepository.kt`](../app/src/main/java/com/spendwise/app/core/data/OfflineFirstBudgetRepository.kt)

Khi lưu giao dịch:

1. Đổi trạng thái thành `PENDING_UPSERT`.
2. Ghi vào Room.
3. Room phát Flow mới.
4. UI cập nhật ngay, kể cả không có mạng.

Sau này repository sẽ gọi WorkManager để đẩy bản ghi pending lên Firestore.

### Bước F3 — khai báo Hilt module `[x]`

Các file:

- [`DatabaseModule.kt`](../app/src/main/java/com/spendwise/app/core/di/DatabaseModule.kt)
- [`RepositoryModule.kt`](../app/src/main/java/com/spendwise/app/core/di/RepositoryModule.kt)

`DatabaseModule` biết cách tạo database, DAO và `Clock`. `RepositoryModule` nói rằng khi code cần `TransactionRepository`, Hilt phải cấp `OfflineFirstTransactionRepository`.

---

## 10. Giai đoạn G — nối một feature hoàn chỉnh từ database lên UI

Feature đầu tiên nên là **thêm giao dịch**, vì dashboard và ngân sách đều cần dữ liệu giao dịch để kiểm chứng.

### Bước G1 — AddTransactionViewModel `[x]`

File: [`AddTransactionViewModel.kt`](../app/src/main/java/com/spendwise/app/feature/transaction/AddTransactionViewModel.kt)

ViewModel:

1. Nhận dữ liệu từ form.
2. Kiểm tra số tiền lớn hơn 0.
3. Tạo `Transaction` và UUID.
4. Gọi `TransactionRepository.save()`.

### Bước G2 — AddTransactionScreen `[x]`

File: [`AddTransactionScreen.kt`](../app/src/main/java/com/spendwise/app/feature/transaction/AddTransactionScreen.kt)

Screen chỉ quản lý input đang gõ và gọi ViewModel. Screen không gọi DAO trực tiếp.

### Bước G3 — danh sách giao dịch `[x]`

Các file:

- [`TransactionListViewModel.kt`](../app/src/main/java/com/spendwise/app/feature/transaction/TransactionListViewModel.kt)
- [`TransactionListScreen.kt`](../app/src/main/java/com/spendwise/app/feature/transaction/TransactionListScreen.kt)

Luồng cập nhật đầy đủ:

```text
Người dùng nhấn Lưu
→ AddTransactionViewModel
→ TransactionRepository
→ TransactionDao upsert
→ Room phát Flow mới
→ TransactionListViewModel nhận list mới
→ Compose tự vẽ lại danh sách
```

Đây là lát cắt end-to-end đầu tiên của project.

### Bước G4 — dashboard `[x]`

Các file:

- [`DashboardViewModel.kt`](../app/src/main/java/com/spendwise/app/feature/dashboard/DashboardViewModel.kt)
- [`DashboardScreen.kt`](../app/src/main/java/com/spendwise/app/feature/dashboard/DashboardScreen.kt)

Dashboard hiện lấy tổng chi tháng và ngân sách qua hai Flow, sau đó `combine` thành một `DashboardUiState`.

### Bước G5 — ngân sách `[x]`

Các file:

- [`BudgetViewModel.kt`](../app/src/main/java/com/spendwise/app/feature/budget/BudgetViewModel.kt)
- [`BudgetScreen.kt`](../app/src/main/java/com/spendwise/app/feature/budget/BudgetScreen.kt)

Người dùng lưu hạn mức vào Room; dashboard quan sát cùng bản ghi nên tự cập nhật số tiền còn lại.

---

## 11. Tiến độ hiện tại của từng chức năng

| Chức năng | Trạng thái | Ghi chú |
|---|---:|---|
| Gradle/project structure | `[x]` | Wrapper và dependency đã resolve |
| Theme và navigation | `[x]` | Có đủ màn hình cấp cao |
| Room schema | `[x]` | Transaction + MonthlyBudget version 1 |
| Thêm/xem giao dịch | `[x]` | Lưu và đọc thật từ Room |
| Sửa giao dịch | `[ ]` | Là phần tiếp theo của Sprint 1 |
| Xóa giao dịch | `[~]` | DAO/repository có soft delete, UI chưa có |
| Dashboard cơ bản | `[x]` | Tổng chi và ngân sách tháng |
| Ngân sách tháng | `[x]` | Thêm/cập nhật local |
| Cảnh báo ngân sách | `[ ]` | Chưa có notification logic |
| Auth | `[~]` | Có contract và màn hình local; chưa nối Firebase |
| Camera | `[~]` | Có permission/dependency và placeholder |
| ML Kit OCR | `[~]` | Có dependency và `ReceiptDraft`; chưa gọi recognizer |
| Receipt parser | `[~]` | Có interface, chưa có implementation |
| Category suggestion | `[~]` | Có interface, chưa có keyword rules |
| Firestore sync | `[~]` | Có `syncState`; chưa có worker/remote adapter |
| Biểu đồ/export | `[ ]` | Là phần nâng cao |

---

## 12. Việc tiếp theo phải làm theo thứ tự nào?

### 12.1. Hoàn thiện CRUD local

Thứ tự file nên sửa/tạo:

1. Thêm hàm update/get detail trong `TransactionDao` nếu cần.
2. Bổ sung API vào `TransactionRepository`.
3. Cập nhật `OfflineFirstTransactionRepository`.
4. Tạo `TransactionDetailViewModel`.
5. Tạo `EditTransactionScreen`.
6. Thêm route edit vào navigation.
7. Thêm nút sửa/xóa ở danh sách hoặc detail.
8. Viết test DAO/repository.

Không làm UI edit trước khi repository có contract rõ ràng.

### 12.2. Làm OCR

Thứ tự đề xuất:

1. Viết `DefaultReceiptTextParser` bằng Kotlin thuần.
2. Viết unit test parser với khoảng 10 raw text hóa đơn mẫu.
3. Viết `KeywordCategorySuggester` và test.
4. Tạo adapter `MlKitReceiptRecognizer` chỉ có nhiệm vụ ảnh → raw text.
5. Tạo `ReceiptCaptureViewModel` điều phối trạng thái.
6. Thay placeholder bằng CameraX preview.
7. Tạo `ReceiptConfirmationScreen` dùng `ReceiptDraft`.
8. Khi người dùng xác nhận, chuyển draft thành `Transaction` và gọi repository.

Lý do parser làm trước camera: parser là Kotlin thuần, test nhanh và không cần thiết bị. Nếu làm CameraX trước, nhóm có thể chụp được ảnh nhưng chưa chứng minh trích đúng dữ liệu.

### 12.3. Làm Firebase Auth và sync

Chỉ bắt đầu sau khi CRUD offline ổn định:

1. Tạo Firebase project và đăng ký Android application ID.
2. Thêm `google-services.json` ở máy dev nhưng không commit file chứa cấu hình nếu nhóm quy định giữ riêng.
3. Bật Email/Password Authentication.
4. Tạo `FirebaseAuthRepository` implement `AuthRepository`.
5. Thay user demo bằng Firebase UID.
6. Thiết kế Firestore collection theo user.
7. Viết Firestore Security Rules.
8. Tạo remote data source.
9. Tạo `SyncWorker` đọc các bản ghi `PENDING_*` từ Room.
10. Chỉ đổi thành `SYNCED` sau khi server trả thành công.
11. Test mất mạng, retry và hai thiết bị.

---

## 13. Cách kiểm tra sau mỗi loại thay đổi

### Sửa build file

```powershell
.\gradlew.bat help
.\gradlew.bat :app:dependencies --configuration debugRuntimeClasspath
```

### Sửa Kotlin/Compose/Room

```powershell
.\gradlew.bat :app:assembleDebug
```

### Sửa code nghiệp vụ thuần hoặc parser

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

### Sửa database schema

Kiểm tra thêm:

- database version đã tăng chưa;
- migration đã có chưa;
- schema JSON trong `app/schemas` đã thay đổi đúng chưa;
- dữ liệu cũ có còn sau nâng cấp không.

### Sửa camera hoặc permission

Phải test trên ít nhất:

- một emulator hoặc device từ chối permission;
- trường hợp cấp permission;
- thiết bị không có camera phù hợp;
- ảnh mờ hoặc xoay ngang.

---

## 14. Cách ghi nhật ký tiến độ từ bây giờ

Sau mỗi buổi, thêm một mục vào cuối tài liệu hoặc tạo file nhật ký riêng theo mẫu:

```markdown
### DD/MM/YYYY — Tên chức năng

- Mục tiêu:
- File đã tạo/sửa:
- Luồng đã chạy được:
- Kiểm thử đã chạy:
- Vấn đề còn lại:
- Người phụ trách:
```

Ví dụ:

```markdown
### 08/09/2026 — Khởi tạo project và local data

- Mục tiêu: tạo sườn offline-first.
- File đã tạo/sửa: Gradle, Manifest, model, Room, repository, navigation và screen.
- Luồng đã chạy được ở source: thêm giao dịch → Room → list/dashboard.
- Kiểm thử: Gradle help và dependency resolution thành công; XML hợp lệ.
- Vấn đề còn lại: môi trường hiện chưa có Android SDK để build APK.
- Người phụ trách: cập nhật theo phân công của nhóm.
```

---

## 15. Cách trình bày ngắn với giảng viên

Có thể mô tả như sau:

> Nhóm bắt đầu bằng việc chốt use case và yêu cầu offline. Sau đó nhóm tạo hệ thống build gồm settings, version catalog, root build và app module. Ở runtime, Manifest mở Application, Application khởi tạo Hilt, MainActivity mở Compose và NavHost điều hướng tới các feature. Về dữ liệu, nhóm thiết kế domain model trước, sau đó tạo Room entity, converter, DAO, database, mapper và repository. Feature đầu tiên được nối end-to-end là thêm giao dịch: Screen gửi input vào ViewModel, ViewModel gọi Repository, Repository ghi Room, Room phát Flow mới để danh sách và dashboard cập nhật. OCR và Firebase được đặt sau local CRUD để không làm mất khả năng offline và giảm rủi ro tích hợp.

Điểm cần nhấn mạnh là project không được xây theo kiểu “làm hết giao diện rồi mới nghĩ database”. Mỗi feature được nối thành một lát cắt hoàn chỉnh từ UI xuống dữ liệu, sau đó mới chuyển sang feature tiếp theo.
