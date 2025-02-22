package br.com.primary.main.dao.generics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String URL = "jdbc:mysql://localhost:3306/teste";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection connection;
	private ConnectionFactory(Connection connection) {}
	public static Connection getConnection() throws SQLException {
		if(connection == null || connection.isClosed()) {
			connection = initConnection();
			return connection;
		}
		return connection;
	}
	private static Connection initConnection() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
