package br.com.primary.main.dao;

import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IVendaDao extends IGenericDAO<Venda, Long> {
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException;
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException;
	public Venda showWithCollection(Long id);
}
