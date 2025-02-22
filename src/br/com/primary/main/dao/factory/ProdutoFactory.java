package br.com.primary.main.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.primary.main.domain.Produto;

public class ProdutoFactory {
	public static Produto convert(ResultSet rs) throws SQLException {
		Produto prod = new Produto();
		prod.setCode(rs.getString("code"));
		prod.setName(rs.getString("name"));
		prod.setDescription(rs.getString("description"));
		prod.setValue(rs.getBigDecimal("value"));
		return prod;
	}
}
