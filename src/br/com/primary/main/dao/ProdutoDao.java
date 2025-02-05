package br.com.primary.main.dao;

import br.com.primary.main.dao.generics.GenericDao;
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
}
