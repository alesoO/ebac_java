package br.com.primary.main.service.generic.jpa;

import br.com.primary.main.dao.generics.jpa.IGenericJapDAO;
import br.com.primary.main.domain.jpa.Persistente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public abstract class GenericJpaService<T extends Persistente, E extends Serializable> implements IGenericJpaService<T,E> {
	protected IGenericJapDAO<T,E> dao;
	
	public GenericJpaService(IGenericJapDAO<T,E> dao) {
		this.dao = dao;
	}
	
	@Override
	public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException {
		return this.dao.add(entity);
	}
	
	@Override
	public void delete(T entity) throws DaoException {
		this.dao.delete(entity);
	}
	
	@Override
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException {
		this.dao.edit(entity);
	}
	
	@Override
	public T show(E value) throws DaoException, MoreThanOneRegisterException, TableException {
		return this.dao.show(value);
	}
	
	@Override
	public Collection<T> showAll() throws DaoException {
		return this.dao.showAll();
	}
}
