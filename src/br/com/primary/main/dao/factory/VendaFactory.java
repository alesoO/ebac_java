package br.com.primary.main.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.primary.main.domain.Cliente;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.Venda.Status;

public class VendaFactory {
	public static Venda convert(ResultSet rs) throws SQLException {
		Cliente client = ClienteFactory.convert(rs);
		Venda sale = new Venda();
		sale.setCliente(client);
		sale.setId(rs.getLong("id_sale"));
		sale.setCode(rs.getString("code"));
		sale.setTotalValue(rs.getBigDecimal("total_value"));
		sale.setSaleDate(rs.getTimestamp("sale_date").toInstant());
		sale.setStatus(Status.getByName(rs.getString("sale_status")));
		return sale;
	}
}
