package br.com.primary.main.service.generic;

import java.io.Serializable;
import java.util.Collection;

import br.com.primary.main.dao.Persistent;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IGenericService<T extends Persistent, E extends Serializable> {
	
	public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public void delete(T entity) throws DaoException;
	
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public T show(E value) throws MoreThanOneRegisterException, TableException, DaoException;
	
	public Collection<T> showAll() throws DaoException;
}
