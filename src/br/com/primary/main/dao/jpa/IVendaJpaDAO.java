package br.com.primary.main.dao.jpa;

import br.com.primary.main.domain.jpa.VendaJpa;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IVendaJpaDAO extends IGenericJapDAO<VendaJpa, Long> {
	public void finishVenda(VendaJpa venda) throws TypeKeyNotFoundException, DaoException;
	public void cancelVenda(VendaJpa venda) throws TypeKeyNotFoundException, DaoException;
	public VendaJpa showWithCollection(Long id);
}
