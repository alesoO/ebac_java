package br.com.primary.main.dao.jpa;

import java.util.Collection;

import br.com.primary.main.domain.jpa.ClienteJpa2;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class ClienteJpaDB3DAO extends GenericJpaDB3DAO<ClienteJpa2, Long> implements IClienteJpaDAO<ClienteJpa2> {
	public ClienteJpaDB3DAO() {
		super(ClienteJpa2.class);
	}
	@Override
	public ClienteJpa add(ClienteJpa  entity) throws TypeKeyNotFoundException, DaoException {
		return null;
	}
	
	@Override
	public void delete(ClienteJpa entity) throws DaoException {
		
	}
	
	@Override
	public ClienteJpa edit(ClienteJpa entity) throws TypeKeyNotFoundException, DaoException {
		return null;
	}
	
	@Override
	public ClienteJpa show(Long id) throws MoreThanOneRegisterException, TableException, DaoException {
		return null;
	}
	
	@Override
	public Collection<ClienteJpa> showAll() throws DaoException {
		return null;
	}
}
