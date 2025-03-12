package br.com.primary.main.service.generic;

import java.io.Serializable;
import java.util.Collection;
import br.com.primary.main.dao.Persistent;
import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public abstract class GenericService<T extends Persistent, E extends Serializable> implements IGenericService<T,E> {
	protected IGenericDAO<T,E> dao;
	
	public GenericService(IGenericDAO<T,E> dao) {
		this.dao = dao;
	}
	
	@Override
	public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException {
		return this.dao.add(entity);
	}
	
	@Override
	public void delete(E value) throws DaoException {
		this.dao.delete(value);
	}
	
	@Override
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException {
		this.edit(entity);
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
