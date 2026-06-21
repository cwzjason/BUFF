# BUFF程序解决方案

## 数据库连接配置
- 数据库URL：`jdbc:mysql://127.0.0.1:3306/db1`
- 用户名：`root`
- 密码：`123456`

---

## v2.0 订单/购物车修复说明

### 问题根因
1. **`orderitems` 表缺失** - `zhangdan.java`/`totallist.java` 引用但 `init_database.sql` 未创建
2. **无用户隔离** - `orderlist`/`totallist` 缺少 `user_id`，多用户共享购物车
3. **无事务保护** - 结算用3个独立连接，不能保证原子性
4. **TRUNCATE TABLE** - 清空所有用户购物车

### 新增文件
| 文件 | 说明 |
|------|------|
| `text/Session.java` | 用户会话管理器(单例) |
| `text/CartService.java` | 购物车业务层(用户隔离CRUD) |
| `text/OrderService.java` | 订单业务层(事务+支付回调验证+数据校验) |
| `text/QuickBuyHelper.java` | 通用购买对话框 |

### 修改文件
| 文件 | 修改 |
|------|------|
| `init_database.sql` | +`orderitems`表, +`user_id`列, +外键 |
| `text/zhangdan.java` | 用Service层, 事务结算, 按用户清空购物车 |
| `text/totallist.java` | 按`user_id`过滤, 展示物品详情 |
| `text/userlogin.java` | 登录时设置Session, 防SQL注入 |
| `text/buy.java` | 用CartService关联用户 |
| `SportGloves/yd_maiami.java` | 用QuickBuyHelper(示例) |

### 数据库迁移(已有数据)
```sql
USE db1;
ALTER TABLE orderlist ADD COLUMN user_id INT NOT NULL DEFAULT 1 AFTER id;
ALTER TABLE totallist ADD COLUMN user_id INT NOT NULL DEFAULT 1 AFTER id;
CREATE TABLE IF NOT EXISTS orderitems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL, name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL, amount INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES totallist(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## 解决方案步骤

### 第一步：安装JavaFX（必需）
1. 下载JavaFX SDK：
   - 访问 https://gluonhq.com/products/javafx/
   - 下载适合你系统的JavaFX SDK（Windows x64）

2. 安装JavaFX：
   - 解压下载的zip文件到 `C:\javafx-sdk-26`（或其他目录）
   - 将JavaFX的bin目录添加到系统PATH

### 第二步：设置MySQL数据库（必需）
1. **安装MySQL**（如果没有）：
   - 下载MySQL Installer：https://dev.mysql.com/downloads/installer/
   - 安装MySQL Server

2. **启动MySQL服务**：
   ```bash
   # Windows服务管理
   net start MySQL
   # 或通过服务管理器启动MySQL服务
   ```

3. **创建数据库和表**：
   ```sql
   -- 使用MySQL命令行或工具（如MySQL Workbench）
   CREATE DATABASE IF NOT EXISTS db1;
   USE db1;
   
   -- 创建用户表
   CREATE TABLE userlogin (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(50) NOT NULL,
       password VARCHAR(50) NOT NULL
   );
   
   -- 创建管理员表
   CREATE TABLE managerlogin (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(50) NOT NULL,
       password VARCHAR(50) NOT NULL
   );
   
   -- 添加测试用户
   INSERT INTO userlogin (name, password) VALUES ('testuser', 'test123');
   INSERT INTO managerlogin (name, password) VALUES ('admin', 'admin123');
   ```

### 第三步：运行程序
#### 方法A：使用Maven运行（推荐）
```bash
cd d:\vibe\javaproject
# 先编译
.\apache-maven-3.9.9\bin\mvn.cmd compile
# 运行程序
.\apache-maven-3.9.9\bin\mvn.cmd javafx:run
```

#### 方法B：直接运行（需要JavaFX）
```bash
cd d:\vibe\javaproject
# 设置JavaFX路径（根据实际安装位置调整）
set JAVA_FX_PATH=C:\javafx-sdk-26\lib
java --module-path "%JAVA_FX_PATH%;lib/*" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "target/classes;lib/*" text.BUFF
```

#### 方法C：使用提供的批处理文件
双击运行 `run.bat` 文件

### 第四步：修改数据库连接（可选）
如果你需要连接到不同的数据库，修改 `src/main/resources/db_config.properties`：
```properties
# 修改为你的数据库配置
db.url=jdbc:mysql://localhost:3306/your_database
db.username=your_username
db.password=your_password
```

## 常见问题解决

### 1. 如果出现 "JavaFX runtime components are missing"
- 确保已安装JavaFX SDK
- 检查JavaFX路径是否正确
- 使用Maven运行时可能需要额外配置

### 2. 如果出现数据库连接错误
```bash
# 检查MySQL服务是否运行
sc query MySQL

# 测试数据库连接
mysql -u root -p123456 -h 127.0.0.1
```

### 3. 如果编译失败
```bash
# 清理并重新编译
.\apache-maven-3.9.9\bin\mvn.cmd clean compile
```

## 数据库迁移到云数据库（如需要）

如果你想要将数据库迁移到云服务（如Supabase、CloudBase等），需要：

1. **修改数据库连接URL**：
   ```java
   // 修改所有数据库连接代码中的URL
   String url = "jdbc:postgresql://your-cloud-db-url:5432/dbname";
   ```

2. **更新依赖**：
   - 修改pom.xml中的数据库驱动
   - 添加云数据库的JDBC驱动

3. **配置安全连接**：
   - 添加SSL配置
   - 更新用户名和密码

## 快速测试步骤

1. 确保MySQL服务运行
2. 运行 `run.bat` 测试程序启动
3. 使用测试账号登录：
   - 用户：testuser / test123
   - 管理员：admin / admin123

## 联系方式
如有进一步问题，请提供具体的错误信息以便进一步诊断。