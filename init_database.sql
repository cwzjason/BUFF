-- ============================================
-- BUFF 购物车系统 - MySQL数据库初始化脚本（v2.1）
-- 数据库: 127.0.0.1:3306  db1  用户: root  密码: 123456
-- WARNING: 使用 CREATE TABLE IF NOT EXISTS 保护已有数据
-- ============================================

-- 1. 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS db1 DEFAULT CHARACTER SET utf8mb4;
USE db1;

-- 2. 购物车表 (购物车清单) — user_id + created_at 支持多用户隔离
CREATE TABLE IF NOT EXISTS orderlist (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT           NOT NULL COMMENT '用户ID',
    name       VARCHAR(100)  NOT NULL COMMENT '商品名称',
    price      DECIMAL(10,2) NOT NULL COMMENT '单价',
    amount     INT           NOT NULL COMMENT '数量',
    created_at DATETIME      DEFAULT NULL COMMENT '添加时间',
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

-- 3. 历史订单表 (总消费记录) — 增加 user_id
CREATE TABLE IF NOT EXISTS totallist (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT           NOT NULL COMMENT '用户ID',
    money   DECIMAL(12,2) NOT NULL COMMENT '金额',
    date    DATETIME      NOT NULL COMMENT '时间',
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历史订单';

-- 4. 订单详情表 (订单内物品明细)
CREATE TABLE IF NOT EXISTS orderitems (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT           NOT NULL COMMENT '订单ID，关联totallist.id',
    name     VARCHAR(100)  NOT NULL COMMENT '商品名称',
    price    DECIMAL(10,2) NOT NULL COMMENT '单价',
    amount   INT           NOT NULL COMMENT '数量',
    INDEX idx_order (order_id),
    FOREIGN KEY (order_id) REFERENCES totallist(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单物品明细';

-- 验证
SELECT '=== orderlist 结构 ===' AS '';
DESCRIBE orderlist;
SELECT '=== totallist 结构 ===' AS '';
DESCRIBE totallist;
SELECT '=== orderitems 结构 ===' AS '';
DESCRIBE orderitems;
SELECT '=== 初始化完成 ===' AS '';
