package br.com.primary.main.dao;

import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.Venda.Status;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class VendaDao extends GenericDao<Venda, String> implements IVendaDao {
	
	@Override
	public Class<Venda> getTypeClass() {
		return Venda.class;
	}
	
	@Override
	public void updateData(Venda entity, Venda entityAdded) {
		entityAdded.setCode(entity.getCode());
		entityAdded.setStatus(entity.getStatus());
	}
	
	@Override
	public void delete(String value) {
		throw new UnsupportedOperationException("Operação não permitida");
	}
	
	@Override
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException {
		venda.setStatus(Status.CONCLUIDA);
		super.edit(venda);
	}
}
