package text;

import java.sql.Timestamp;

/**
 * 历史订单摘要模型 — 用于 totallist TableView 展示
 * 字段对应 JavaFX TableColumn 的 PropertyValueFactory 绑定
 */
public class OrderSummary {
    private int id;
    private int money;
    private Timestamp date;
    private int itemCount;

    public OrderSummary(int id, int money, Timestamp date, int itemCount) {
        this.id = id;
        this.money = money;
        this.date = date;
        this.itemCount = itemCount;
    }

    // ===== Getters (PropertyValueFactory 需要) =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}
