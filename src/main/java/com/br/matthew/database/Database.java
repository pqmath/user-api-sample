package com.br.matthew.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;

import com.br.matthew.exceptions.JdbcException;

@Configuration
public class Database {

	private static Connection conn = null;

	public DataSource dataSource() {
		return new DataSource() {
			@Override
			public Connection getConnection() throws SQLException {
				return Database.getConnection();
			}

			@Override
			public Connection getConnection(String username, String password) throws SQLException {
				return Database.getConnection();
			}

			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public PrintWriter getLogWriter() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setLoginTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub

			}

			@Override
			public int getLoginTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

		};
	}

	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("spring.datasource.url");
				String username = props.getProperty("spring.datasource.username");
				String password = props.getProperty("spring.datasource.password");
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				throw new JdbcException(e.getMessage());
			}
		}
		return conn;
	}

	static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new JdbcException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (InputStream inputStream = Database.class.getClassLoader().getResourceAsStream("application.properties")) {
			if (inputStream == null) {
				throw new JdbcException("application.properties not found!");
			}
			Properties props = new Properties();
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw new JdbcException(e.getMessage());
		}
	}

	public void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new JdbcException(e.getMessage());
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new JdbcException(e.getMessage());
			}
		}
	}

}
