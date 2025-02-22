package br.com.primary.main.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Cliente;
import br.com.primary.main.domain.Produto;

public class ProdutoDao extends GenericDao<Produto, String> implements IProdutoDao {
	public ProdutoDao() {
		super();
	}
	
	@Override
	public Class<Produto> getTypeClass() {
		return Produto.class;
	}
	
	@Override
	public void updateData(Produto entity, Produto entityAdded) {
		entityAdded.setCode(entity.getCode());
		entityAdded.setDescription(entity.getDescription());
		entityAdded.setName(entity.getName());
		entityAdded.setValue(entity.getValue());
	}
	
	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Product ");
		sb.append("(ID, code, name, description, value, category)");
		sb.append("VALUES (nextval('sq_product'),?,?,?,?,?,?)");
		return sb.toString();
	}
	
	protected void setParametersQueryInsert(PreparedStatement stmInsert, Produto entity) throws SQLException {
		stmInsert.setString(1, entity.getCode());
		stmInsert.setString(2, entity.getName());
		stmInsert.setString(3, entity.getDescription());
		stmInsert.setBigDecimal(4, entity.getValue());
		stmInsert.setString(5, entity.getCategory());
	}
	
	@Override
	protected String getQueryDelete() {
		return "DELETE FROM Product WHERE code = ?";
	}
	
	@Override
	protected void setParametersQueryDelete(PreparedStatement stmDelete, String value) throws SQLException {
		stmDelete.setString(1, value);
	}
	
	@Override
	protected String getQueryUpdate() {
		StringBuilder sb = new StringBuilder();;
		sb.append("UPDATE Product ");
		sb.append("SET code = ?,");
		sb.append("name = ?,");
		sb.append("description = ?,");
		sb.append("value = ?,");
		sb.append("category = ?,");
		sb.append(" WHERE code = ?");
		return sb.toString();
	}
	
	protected void setParametersQueryUpdate(PreparedStatement stmUpdate, Produto entity) throws SQLException {
		stmUpdate.setString(1, entity.getCode());
		stmUpdate.setString(2, entity.getName());
		stmUpdate.setString(3, entity.getDescription());
		stmUpdate.setBigDecimal(4, entity.getValue());
		stmUpdate.setString(5, entity.getCategory());
	}
	
	@Override
	protected void setParametersQuerySelect(PreparedStatement stmSelect, String value) throws SQLException {
		stmSelect.setString(1, value);
	}
}
