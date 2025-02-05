package br.com.primary.main.dao;

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
		entityAdded.setEnd(entity.getEnd());
		entityAdded.setState(entity.getState());
		entityAdded.setName(entity.getName());
		entityAdded.setNumber(entity.getNumber());
		entityAdded.setTel(entity.getTel());
	}
}
