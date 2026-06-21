# BUFF - CS:GO Skin Trading Platform

[English](README.md) | [中文](README_CN.md)

A JavaFX desktop application simulating the [BUFF](https://buff.163.com/) marketplace for CS:GO (Counter-Strike: Global Offensive) weapon skins. Browse, buy, and manage your virtual skin collection with a mobile-app-style UI.

## ✨ Features

- **User System** — Register, login, and session management
- **Skin Catalog** — 50+ CS:GO skins across 8 weapon categories with images & prices
- **Shopping Cart** — Add/remove items, adjust quantities, view total price
- **Order Checkout** — Transaction-protected order placement with MySQL
- **Order History** — View past orders and itemized purchase details
- **Data Integrity Guard** — Retry mechanism, local snapshot backup, automatic repair
- **Mobile-style UI** — 320×670 frameless windows, dark theme

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 26 |
| UI Framework | JavaFX 26 |
| Database | MySQL 8.0 |
| Build Tool | Maven 3.9.9 |
| JDBC Driver | MySQL Connector 8.0.33 |

## 📦 Project Structure

```
javaproject/
├── text/                  # Core business logic (28 Java files)
│   ├── BUFF.java          # App entry point
│   ├── main.java          # Home page / product catalog
│   ├── Register.java      # User registration
│   ├── Test.java          # User login
│   ├── CartService.java   # Shopping cart service
│   ├── OrderService.java  # Order service
│   ├── Session.java       # User session (singleton)
│   └── ...
├── AK/                    # AK-47 skins (8 skins + catalog)
├── AWP/                   # AWP sniper skins (6 skins + catalog)
├── BufferflyKnife/        # Butterfly knife skins (5 skins + catalog)
├── Karambit/              # Karambit knife skins (5 skins + catalog)
├── M4A1/                  # M4A1-S skins (4 skins + catalog)
├── M9Knife/               # M9 Bayonet skins (5 skins + catalog)
├── SpecialistGloves/      # Specialist gloves (4 skins + catalog)
├── SportGloves/           # Sport gloves (6 skins + catalog)
├── Manager/               # Admin module
├── util/                  # Database utilities
├── image/                 # 103 skin images
├── pom.xml                # Maven configuration
├── init_database.sql      # Database schema
├── seed_demo_data.sql     # Demo data
└── README.md
```

## 🚀 Quick Start

### Prerequisites

- JDK 26+
- MySQL 8.0+ running on `localhost:3306`
- Maven 3.9+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/buff-csgo-market.git
   cd buff-csgo-market
   ```

2. **Initialize the database**
   ```bash
   mysql -u root -p < init_database.sql
   ```

3. **Update database credentials** (if needed)
   
   Edit `util/DBUtil.java`:
   ```java
   // Default: root / 123456 @ jdbc:mysql://127.0.0.1:3306/db1
   ```

4. **Build and run**
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

### Demo Data

To seed sample data for testing:
```bash
mysql -u root -p < seed_demo_data.sql
```

## 🎮 Skin Categories

| Category | Example Skins | Price Range |
|----------|--------------|-------------|
| AK-47 | Fire Serpent, Vulcan, Empress | $267 – $2,770 |
| M4A1-S | Printstream, Icarus Fell, Knight | $1,069 – $2,963 |
| AWP | Dragon Lore, Gungnir, Medusa | $1,434 – $9,085 |
| Butterfly Knife | Doppler, Fade, Slaughter | $3,173 – $5,769 |
| Karambit | Marble Fade, Autotronic, Crimson Web | $1,614 – $4,296 |
| M9 Bayonet | Doppler, Marble Fade, Tiger Tooth | $3,814 – $15,962 |
| Sport Gloves | Superconductor, Pandora's Box, Amphibious | $3,350 – $15,962 |
| Specialist Gloves | Tiger Strike, Fade, Crimson Web | $2,460 – $4,598 |

## 📋 Database Schema

- **`userlogin`** — User accounts (id, name, password)
- **`orderlist`** — Shopping cart items (per-user isolation)
- **`totallist`** — Completed orders with timestamps
- **`orderitems`** — Order line items with foreign key to `totallist`

## ⚠️ Notes

- Passwords are stored in plain text — **not suitable for production use**
- Database credentials are hardcoded in `DBUtil.java`
- Source code follows a flat package structure (non-standard Maven layout)
- All windows use `StageStyle.UNDECORATED` for a frameless mobile-app appearance

## 📝 License

This project is for educational purposes only. All skin names and images are the property of their respective owners.
