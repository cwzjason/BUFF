package text;

/**
 * Cart item model class
 */
public class CartItem {
    private int id;
    private String name;
    private int price;
    private int amount;
    private String createdAt;  // purchase time, formatted for display

    public CartItem(int id, String name, int price, int amount, String createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    // Backward-compatible constructor
    public CartItem(String name, int price, int amount) {
        this(0, name, price, amount, "");
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
