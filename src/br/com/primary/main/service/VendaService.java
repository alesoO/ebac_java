package br.com.primary.main.service;

import java.io.ObjectInputFilter;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.primary.main.dao.IVendaDao;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.Venda.Status;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;
import br.com.primary.main.service.generic.GenericService;

public class VendaService extends GenericService<Venda, String> implements IVendaService {
    IVendaDao dao;

    @Inject
    public VendaService(IVendaDao dao) {
        super(dao);
        this.dao = dao;
    }
    @Override
    public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
        venda.setStatus(Status.CONCLUIDA);
        dao.finishVenda(venda);
    }
    @Override
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
        venda.setStatus(Status.CANCELADA);
        dao.cancelVenda(venda);
    }
    @Override
	public Venda showWithCollection(Long id) {
        return dao.showWithCollection(id);
    }
    @Override
    public Venda add(Venda entity) throws TypeKeyNotFoundException, DaoException {
        entity.setStatus(Status.INICIADA);
        return super.add(entity);
    }
}
