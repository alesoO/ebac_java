package br.com.primary.main.dao;

import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IVendaDao extends IGenericDAO<Venda, String> {
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException;
}
