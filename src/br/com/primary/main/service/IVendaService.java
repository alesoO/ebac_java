package br.com.primary.main.service;

import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IVendaService extends IGenericDAO<Venda, Long> {
    public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException;
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException;
	public Venda showWithCollection(Long id);
    
}
