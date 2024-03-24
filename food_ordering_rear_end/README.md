# 訂餐系統

這個項目是一個食物訂購系統的後端，提供了管理餐點、購物車和使用者等功能。以下是主要的控制器和服務介紹：

## mealsController

這個控制器管理餐點相關的操作，包括了以下功能：

- **取得Meals跟item對應得item項目值**：
    - `GET /mealsList/findItemCountByMeals`
    - 返回一個包含了item項目值的列表。

- **篩選Meals對應的item並回傳Page**：
    - `GET /mealsList/findAllByItem/{pageNo}/{item}`
    - 返回一個指定頁面的MealsEntity列表。

- **新增cart**：
    - `POST /mealsList/insertIntoCart`
    - 添加一個新的購物車項目。

- **查詢cart**：
    - `POST /mealsList/cartEntityByUsername/{username}`
    - 返回指定用戶的購物車列表。

- **刪除cart**：
    - `Delete /mealsList/deleteCartEntityById/{id}`
    - 刪除指定ID的購物車項目。

- **更新cart quantity數量**：
    - `POST /mealsList/updateCartEntityById/{quantity}/{id}`
    - 更新指定ID的購物車項目的數量。

## userController

這個控制器管理使用者相關的操作，包括了以下功能：

- **新增會員**：
    - `POST /userController/addUser`
    - 添加一個新的使用者。

- **檢查登入**：
    - `POST /userController/LoginUser`
    - 檢查使用者登入信息。

- **更新會員資料**：
    - `POST /userController/updateUser`
    - 更新使用者資料。

- **依據username刪除帳號**：
    - `Delete /userController/deleteUser/{username}`
    - 刪除指定username的使用者帳號。

- **取得 user Session**：
  - `GET /userController/getSession`
  - 返回當前用戶的 session 資訊。

- **登出 刪除 user Session**：
  - `PUT /userController/logout`
  - 登出當前用戶並刪除 session 資訊。

## 使用技術

這個項目使用了Spring Boot框架來構建RESTful API，以及Spring的相關模組，包括Spring MVC和Spring Data JPA。此外，還使用了Jakarta Servlet來處理HTTP請求和響應。

## 貢獻

如果您發現任何問題或者有改進的建議，請隨時提交issue或者pull request。我們歡迎您的貢獻！

## 作者

這個項目的作者是 [Leaf](https://github.com/LeafTW/food_order)。
