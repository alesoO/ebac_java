package br.com.primary.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.primary.main.dao.jdbc.ConnectionFactory;
import br.com.primary.main.domain.Cliente;

public class ClienteDao implements IClienteDao {

	@Override
	public Integer add(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "INSERT INTO client (code, name) VALUES (?,?)";
			stm = connection.prepareStatement(sql);
			stm.setString(0, cliente.getCode());
			stm.setString(1, cliente.getName());
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Cliente show(String code) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "select * from client where code = ?";
			stm = connection.prepareStatement(sql);
			stm.setString(1, code);
			rs = stm.executeQuery();
			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getLong("id"));
				cliente.setCode(rs.getString("code"));
				cliente.setName(rs.getString("name"));
			}
			return cliente;
		} catch(Exception e) {
			throw e;
		} finally {
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Integer delete(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "DELETE FROM client WHERE code = ?";
			stm = connection.prepareStatement(sql);
			stm.setString(1, cliente.getCode());
			return stm.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}
