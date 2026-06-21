# BUFF — CS:GO 饰品模拟交易平台

基于 JavaFX 的桌面应用，模拟 [BUFF](https://buff.163.com/) 市场进行 CS:GO 武器皮肤交易。支持浏览、购买和管理虚拟皮肤收藏，采用手机 App 风格的 UI 界面。

## ✨ 功能特性

- **用户系统** — 注册、登录、Session 会话管理
- **皮肤目录** — 50+ 款 CS:GO 皮肤，覆盖 8 大武器类别，含图片与价格
- **购物车** — 添加/移除商品、调整数量、显示总价
- **订单结算** — 基于 MySQL 事务保护的购买流程
- **订单历史** — 查看历史订单及购买明细
- **数据保护机制** — 重试机制、本地快照备份、自动修复
- **手机风格界面** — 320×670 无边框窗口、深色主题

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 开发语言 | Java 26 |
| UI 框架 | JavaFX 26 |
| 数据库 | MySQL 8.0 |
| 构建工具 | Maven 3.9.9 |
| JDBC 驱动 | MySQL Connector 8.0.33 |

## 📦 项目结构

```
javaproject/
├── text/                  # 核心业务逻辑（28 个 Java 文件）
│   ├── BUFF.java          # 应用入口
│   ├── main.java          # 首页 / 商品目录
│   ├── Register.java      # 用户注册
│   ├── Test.java          # 用户登录
│   ├── CartService.java   # 购物车服务
│   ├── OrderService.java  # 订单服务
│   ├── Session.java       # 用户会话（单例模式）
│   └── ...
├── AK/                    # AK-47 皮肤（8 款 + 分类页）
├── AWP/                   # AWP 狙击皮肤（6 款 + 分类页）
├── BufferflyKnife/        # 蝴蝶刀皮肤（5 款 + 分类页）
├── Karambit/              # 爪子刀皮肤（5 款 + 分类页）
├── M4A1/                  # M4A1-S 皮肤（4 款 + 分类页）
├── M9Knife/               # M9 刺刀皮肤（5 款 + 分类页）
├── SpecialistGloves/      # 专业手套（4 款 + 分类页）
├── SportGloves/           # 运动手套（6 款 + 分类页）
├── util/                  # 数据库工具类
├── image/                 # 103 张皮肤图片
├── pom.xml                # Maven 配置
├── init_database.sql      # 数据库建表脚本
├── seed_demo_data.sql     # 演示数据脚本
└── README.md
```

## 🚀 快速开始

### 环境要求

- JDK 26+
- MySQL 8.0+ （运行于 `localhost:3306`）
- Maven 3.9+

### 搭建步骤

1. **克隆仓库**
   ```bash
   git clone https://github.com/cwzjason/BUFF.git
   cd BUFF
   ```

2. **初始化数据库**
   ```bash
   mysql -u root -p < init_database.sql
   ```

3. **修改数据库配置**（如需要）

   编辑 `util/DBUtil.java`：
   ```java
   // 默认：root / 123456 @ jdbc:mysql://127.0.0.1:3306/db1
   ```

4. **构建并运行**
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

### 演示数据

导入示例数据用于测试：
```bash
mysql -u root -p < seed_demo_data.sql
```

## 🎮 皮肤分类

| 分类 | 示例皮肤 | 价格范围 |
|------|---------|---------|
| AK-47 | 火蛇、火神、女皇 | $267 – $2,770 |
| M4A1-S | 印花集、伊卡洛斯陨落、骑士 | $1,069 – $2,963 |
| AWP | 巨龙传说、永恒之枪、美杜莎 | $1,434 – $9,085 |
| 蝴蝶刀 | 多普勒、渐变之色、屠夫 | $3,173 – $5,769 |
| 爪子刀 | 大理石渐变、自动液压、深红之网 | $1,614 – $4,296 |
| M9 刺刀 | 多普勒、大理石渐变、虎牙 | $3,814 – $15,962 |
| 运动手套 | 超导体、潘多拉之盒、两栖 | $3,350 – $15,962 |
| 专业手套 | 老虎袭击、渐变之色、深红之网 | $2,460 – $4,598 |

## 📋 数据库设计

- **`userlogin`** — 用户账户（id、name、password）
- **`orderlist`** — 购物车商品（按用户隔离）
- **`totallist`** — 已完成的订单（含时间戳）
- **`orderitems`** — 订单明细（外键关联 totallist）

## ⚠️ 注意事项

- 密码以明文存储 — **不可用于生产环境**
- 数据库凭据硬编码在 `DBUtil.java` 中
- 源码采用扁平包结构（非标准 Maven 目录布局）
- 所有窗口使用 `StageStyle.UNDECORATED` 实现无边框手机 App 外观

## 📝 许可证

本项目仅供学习用途。所有皮肤名称和图片归其各自所有者所有。
