package br.com.primary.main.dao.jpa;

import java.io.Serializable;
import java.util.Collection;

import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IGenericJapDAO <T extends Persistente, E extends Serializable> {
	
	public T add(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public void delete(T entity) throws DaoException;
	
	public T edit(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public T show(E id) throws MoreThanOneRegisterException, TableException, DaoException;
	
	public Collection<T> showAll() throws DaoException;
	
}
