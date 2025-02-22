package br.com.primary.main.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Cliente;

public class ClienteDao extends GenericDao<Cliente, Long> implements IClienteDao {
	
	public ClienteDao() {
		super();
	}
	
	@Override
	public Class<Cliente> getTypeClass() {
		return Cliente.class;
	}
	
	@Override
	public void updateData(Cliente entity, Cliente entityAdded) {
		entityAdded.setCity(entity.getCity());
		entityAdded.setCpf(entity.getCpf());
		entityAdded.setAddress(entity.getAddress());
		entityAdded.setState(entity.getState());
		entityAdded.setName(entity.getName());
		entityAdded.setNumber(entity.getNumber());
		entityAdded.setTel(entity.getTel());
		entityAdded.setCep(entity.getCep());
	}
	
	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Client ");
		sb.append("(ID, name, CPF, TEL, address, number, city, state, CEP)");
		sb.append("VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?,?)");
		return sb.toString();
	}
	
	protected void setParametersQueryInsert(PreparedStatement stmInsert, Cliente entity) throws SQLException {
		stmInsert.setString(1, entity.getCity());
		stmInsert.setLong(2, entity.getCpf());
		stmInsert.setString(3, entity.getAddress());
		stmInsert.setString(4, entity.getState());
		stmInsert.setString(5, entity.getName());
		stmInsert.setLong(6, entity.getNumber());
		stmInsert.setLong(7, entity.getTel());
		stmInsert.setString(8, entity.getCep());
	}
	
	@Override
	protected String getQueryDelete() {
		return "DELETE FROM Client WHERE CPF = ?";
	}
	
	@Override
	protected void setParametersQueryDelete(PreparedStatement stmDelete, Long value) throws SQLException {
		stmDelete.setLong(1,value);
	}
	
	@Override
	protected String getQueryUpdate() {
		StringBuilder sb = new StringBuilder();;
		sb.append("UPDATE Client ");
		sb.append("SET name = ?,");
		sb.append("TEL = ?,");
		sb.append("address = ?,");
		sb.append("number = ?,");
		sb.append("city = ?,");
		sb.append("state = ?,");
		sb.append("CEP = ?");
		sb.append(" WHERE CPF = ?");
		return sb.toString();
	}
	
	protected void setParametersQueryUpdate(PreparedStatement stmUpdate, Cliente entity) throws SQLException {
		stmUpdate.setString(1, entity.getName());
		stmUpdate.setLong(2, entity.getTel());
		stmUpdate.setString(3, entity.getAddress());
		stmUpdate.setLong(4, entity.getNumber());
		stmUpdate.setString(5, entity.getCity());
		stmUpdate.setString(6, entity.getState());
		stmUpdate.setString(7, entity.getCep());
		stmUpdate.setLong(8, entity.getCpf());
	}
	
	@Override
	protected void setParametersQuerySelect(PreparedStatement stmSelect, Long value) throws SQLException {
		stmSelect.setLong(1, value);
	}
}
