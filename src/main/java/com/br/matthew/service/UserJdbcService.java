package com.br.matthew.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.br.matthew.entity.User;
import com.br.matthew.exceptions.JdbcException;
import com.br.matthew.repository.UserRepositoryJdbc;

public class UserJdbcService implements UserRepositoryJdbc {

	private Connection conn;

	public UserJdbcService(Connection conn) {
		this.conn = conn;
	}

	public void insert(User user) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("" + "INSERT INTO user " + "name, surname" + "VALUES " + "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getName());
			st.setString(2, user.getSurname());

			st.close();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}

	}

	public void update(User user) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE user " + "SET name = ?" + "surname = ?" + "WHERE id = ? ");
			st.setString(2, user.getSurname());
			st.setLong(3, user.getId());

			st.executeUpdate();
			st.close();
			st.setString(1, user.getName());
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}
	}

	public void deleteById(Long id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM user WHERE id = ?");
			st.setLong(1, id);
			st.close();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}
	}

	public User findById(Long id) {
		PreparedStatement st;
		try {
			st = conn.prepareStatement("SELECT user WHERE user.id = ?");
			ResultSet rs = null;
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				User user = instantiateUser(rs);
				rs.close();
				st.close();
				return user;
			}
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}
		return null;
	}

	private User instantiateUser(ResultSet rs) throws SQLException {

		User user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));
		user.setSurname(rs.getString("surname"));
		return user;
	}

	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT user.*");
			rs = st.executeQuery();

			List<User> list = new ArrayList<>();
			if (rs.next()) {
				while (rs.next()) {
					User user = instantiateUser(rs);
					list.add(user);
				}
				return list;
			}
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}
		return null;
	}

}
