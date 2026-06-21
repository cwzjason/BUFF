package text;

import java.sql.Timestamp;
import java.util.Date;

public class LIST {
	int id;
	int money;
	Timestamp date;
	public LIST(int id, int money, Timestamp date) {
	
		this.id = id;
		this.money = money;
		this.date = date;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

}
