package text;

public class OrderList {

	private String name;
	private int price;
	private int amount;
	private String date;
	
	public OrderList(String name, int price, int amount, String date) {
		
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount =amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
