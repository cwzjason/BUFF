package Manager;

import util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class getmanager {
	public static 	Manager getManager(String name1, String password1) {
		Manager manager= null;
		try {
			String url = "jdbc:mysql://127.0.0.1:3306/db1" + "";
			String username = "root";
			String password = "123456";
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT * FROM  managerlogin  where name = '" + name1 + "' " + "and password = '" + password1 + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt("id"));
				manager.setName(rs.getString("name"));
				manager.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		return manager;
	}

}

