package com.br.matthew.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.br.matthew.database.Database;
import com.br.matthew.entity.User;
import com.br.matthew.exceptions.JdbcException;
import com.br.matthew.repositories.UserJdbcRepository;

@Configuration
public class UserJdbcService implements UserJdbcRepository {

	private Connection conn;

	@Autowired
	Database db;

	public UserJdbcService() {
		this.conn = Database.getConnection();
	}

	public User create(User user) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO user (name, surname) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getName());
			st.setString(2, user.getSurname());

			st.executeUpdate();
			rs = st.getGeneratedKeys();
			return null;
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		} finally {
			db.closeResultSet(rs);
			db.closeStatement(st);
		}
	}

	public User update(User user) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE user SET name = ?, surname = ? WHERE id = ?");
			st.setString(1, user.getName());
			st.setString(2, user.getSurname());
			st.setLong(3, user.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		} finally {
			db.closeStatement(st);
		}
		return user;
	}

	public void deleteById(Long id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM user WHERE id = ?");
			st.setLong(1, id);
			st.execute();
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		} finally {
			db.closeStatement(st);
		}
	}

	public User findById(Long id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM user WHERE user.id = ?");
			ResultSet rs = null;
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				User user = instantiateUser(rs);
				db.closeResultSet(rs);
				return user;
			}
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		} finally {
			db.closeStatement(st);
		}
		return null;
	}

	User instantiateUser(ResultSet rs) {

		try {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			return user;
		} catch (SQLException e) {
			throw new JdbcException(e.getMessage());
		}
	}

	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM user");
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
		} finally {
			db.closeResultSet(rs);
			db.closeStatement(st);
		}
		return null;
	}

}
