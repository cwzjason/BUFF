-- ============================================
-- BUFF 电商 - 演示数据种子脚本
-- 使用方法: mysql -u root -p123456 < seed_demo_data.sql
-- 假设用户ID为 1 (admin)
-- 请在确认订单和购物车为空时运行
-- ============================================

USE db1;

-- 假设当前登录用户 ID = 1，请根据实际情况修改
SET @uid = 1;

-- ============================================
-- 1. 购物车数据 (3件商品)
-- ============================================
INSERT INTO orderlist(user_id, name, price, amount, created_at) VALUES
(@uid, 'AK-47 | Redline',                  267.00,  2, NOW()),
(@uid, 'M4A1-S | Printstream',             432.00,  1, NOW()),
(@uid, 'Butterfly Knife | Black Laminate',  1728.00, 1, NOW());

SELECT CONCAT('购物车: 已添加 ', ROW_COUNT(), ' 件商品') AS CartResult;

-- ============================================
-- 2. 历史订单记录 (2笔订单)
-- ============================================

-- 订单1: Sport Gloves | Vice ($5306)
INSERT INTO totallist(user_id, money, date) VALUES
(@uid, 5306.00, '2025-06-10 14:30:00');

SET @order1_id = LAST_INSERT_ID();

INSERT INTO orderitems(order_id, name, price, amount) VALUES
(@order1_id, 'Sport Gloves | Vice', 5306.00, 1);

-- 订单2: AWP Dragon Lore ($12733) + AK-47 Redline ($267)
INSERT INTO totallist(user_id, money, date) VALUES
(@uid, 13000.00, '2025-06-15 09:20:00');

SET @order2_id = LAST_INSERT_ID();

INSERT INTO orderitems(order_id, name, price, amount) VALUES
(@order2_id, 'AWP | Dragon Lore', 12733.00, 1),
(@order2_id, 'AK-47 | Redline',     267.00, 1);

SELECT CONCAT('历史订单: 已添加 ', ROW_COUNT(), ' 条明细') AS OrderResult;

-- ============================================
-- 验证
-- ============================================
SELECT '=== 购物车 ===' AS '', o.id, o.name, o.price, o.amount
FROM orderlist o WHERE o.user_id = @uid;

SELECT '=== 历史订单 ===' AS '', t.id, t.money, t.date,
  (SELECT COUNT(*) FROM orderitems WHERE order_id = t.id) AS items
FROM totallist t WHERE t.user_id = @uid;
