## Food Ordering System API 使用指南

此應用程式提供了一系列 API，用於處理食物訂購系統的相關功能。以下是每個端點的功能和使用方式：

### 1. 新增會員

- **URL:** `/userController/addUser`
- **HTTP 方法:** POST
- **描述:** 新增一個新的會員到系統中。
- **請求體:** 傳入 JSON 格式的 `userEntity` 對象，其中包含新會員的相關資訊。
- **回應:** 若成功新增會員，則回傳 `true`；否則回傳 `false`。

範例請求：
```json
POST /userController/addUser
Content-Type: application/json

{
  "username": "example_user",
  "password": "example_password",
  "email": "user@example.com"
}
```

範例回應：
```json
true
```

### 2. 檢查是否登入成功

- **URL:** `/userController/queryUser`
- **HTTP 方法:** POST
- **描述:** 檢查用戶是否成功登入系統。
- **請求體:** 傳入 JSON 格式的 `userEntity` 對象，其中包含用戶登入所需的資訊。
- **回應:** 回傳成功登入的用戶資訊，若未成功則回傳空值。

範例請求：
```json
POST /userController/queryUser
Content-Type: application/json

{
  "username": "example_user",
  "password": "example_password"
}
```

範例回應：
```json
{
  "userId": 1,
  "username": "example_user",
  "email": "user@example.com"
}
```

### 注意事項

- 所有的請求和回應都是以 JSON 格式進行。
- 若要存取這些 API，請確保已在 HTTP 標頭中設定正確的內容類型（`Content-Type: application/json`）。
- 請注意身份驗證和授權，以確保系統安全。
- 如需進一步了解每個端點的詳細內容，請參閱相應的控制器類。

以上是使用此 Food Ordering System API 的指南。如果有任何疑問或需要進一步協助，請隨時聯繫我們的開發團隊。