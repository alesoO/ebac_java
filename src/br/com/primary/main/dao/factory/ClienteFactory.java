package br.com.primary.main.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.primary.main.domain.Cliente;

public class ClienteFactory {
	public static Cliente convert(ResultSet rs) throws SQLException {
		Cliente client = new Cliente();
		client.setName(rs.getString("name"));
		client.setCpf(rs.getLong("cpf"));
		client.setTel(rs.getLong("tel"));
		client.setAddress(rs.getString("address"));
		client.setNumber(rs.getInt("number"));
		client.setCity(rs.getString("city"));
		client.setState(rs.getString("state"));
		client.setCep(rs.getString("cep"));
		return client;
	}
}
