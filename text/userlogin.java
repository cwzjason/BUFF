package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class getuser {
	public static User getUser(String name1, String password1) {
		User user = null;
		try {
			Connection conn = DBUtil.getConnection();
			// 使用 PreparedStatement 防止SQL注入
			String sql = "SELECT * FROM `userlogin` WHERE name = ? AND password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name1);
			ps.setString(2, password1);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));

				// 登录成功后设置用户会话
				Session.getInstance().login(user);
			}
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

}
