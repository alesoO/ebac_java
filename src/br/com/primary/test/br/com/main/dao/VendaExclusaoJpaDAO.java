package br.com.primary.test.br.com.main.dao;

import br.com.primary.main.dao.generics.jpa.GenericJpaDB1DAO;
import br.com.primary.main.dao.jpa.IVendaJpaDAO;
import br.com.primary.main.domain.jpa.VendaJpa;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class VendaExclusaoJpaDAO extends GenericJpaDB1DAO<VendaJpa, Long> implements IVendaJpaDAO {
    public VendaExclusaoJpaDAO() {
        super(VendaJpa.class);
    }
    @Override
    public void finishVenda(VendaJpa venda) throws TypeKeyNotFoundException, DaoException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }
    @Override
	public void cancelVenda(VendaJpa venda) throws TypeKeyNotFoundException, DaoException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }
    @Override
	public VendaJpa showWithCollection(Long id) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }
}
