package br.com.primary.main.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.primary.main.domain.Produto;
import br.com.primary.main.domain.ProdutoQuantidade;

public class ProdutoQuantidadeFactory {
	public static ProdutoQuantidade convert(ResultSet rs) throws SQLException {
		Produto prod = ProdutoFactory.convert(rs);
		ProdutoQuantidade prodQ = new ProdutoQuantidade();
		prodQ.setProduct(prod);
		prodQ.setId(rs.getLong("id"));
		prodQ.setQuantity(rs.getInt("quantity"));
		prodQ.setTotalValue(rs.getBigDecimal("total_value"));
		return prodQ;
	}
}
