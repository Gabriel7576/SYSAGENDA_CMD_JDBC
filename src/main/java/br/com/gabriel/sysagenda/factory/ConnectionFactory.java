package br.com.gabriel.sysagenda.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() {
		
		try {
			String Jdbculr = "jdbc:oracle:thin:@kanto:1521:orcl";
			String user = "SYSAGENDA";
			String password = "DIGALA";
			return DriverManager.getConnection(Jdbculr, user, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
