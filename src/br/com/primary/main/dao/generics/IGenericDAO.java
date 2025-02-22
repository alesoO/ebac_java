package br.com.primary.main.dao.generics;

import br.com.primary.main.dao.Persistent;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistent, E extends Serializable> {
	
	public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public void delete(E value) throws DaoException;
	
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public T show(E value) throws MoreThanOneRegisterException, TableException, DaoException;
	
	public Collection<T> showAll() throws DaoException;
}
