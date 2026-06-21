-- ============================================
-- BUFF 数据库迁移脚本 — 从 v1 升级到 v2
-- 执行此脚本以支持用户隔离 + 订单物品明细
-- ============================================

USE db1;

-- 1. orderlist 增加 user_id 列（如果不存在）
--    给已存在的行填充默认值 1（关联到第一个用户）
SET @exist := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA='db1' AND TABLE_NAME='orderlist' AND COLUMN_NAME='user_id');
SET @sqlstmt := IF(@exist = 0,
    'ALTER TABLE orderlist ADD COLUMN user_id INT NOT NULL DEFAULT 1 AFTER id, ADD INDEX idx_user (user_id)',
    'SELECT ''orderlist.user_id already exists'' AS info');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. totallist 增加 user_id 列
SET @exist := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA='db1' AND TABLE_NAME='totallist' AND COLUMN_NAME='user_id');
SET @sqlstmt := IF(@exist = 0,
    'ALTER TABLE totallist ADD COLUMN user_id INT NOT NULL DEFAULT 1 AFTER id, ADD INDEX idx_user (user_id)',
    'SELECT ''totallist.user_id already exists'' AS info');
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3. 创建 orderitems 订单详情表（如果不存在）
CREATE TABLE IF NOT EXISTS orderitems (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT           NOT NULL COMMENT '订单ID',
    name     VARCHAR(100)  NOT NULL COMMENT '商品名称',
    price    DECIMAL(10,2) NOT NULL COMMENT '单价',
    amount   INT           NOT NULL COMMENT '数量',
    INDEX idx_order (order_id),
    FOREIGN KEY (order_id) REFERENCES totallist(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单物品明细';

-- 验证结果
SELECT '=== 迁移后 orderlist 结构 ===' AS '';
DESCRIBE orderlist;
SELECT '=== 迁移后 totallist 结构 ===' AS '';
DESCRIBE totallist;
SELECT '=== 迁移后 orderitems 结构 ===' AS '';
DESCRIBE orderitems;
SELECT '=== 迁移完成 ===' AS '';
