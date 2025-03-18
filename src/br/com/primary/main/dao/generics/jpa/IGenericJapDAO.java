package br.com.primary.main.dao.generics.jpa;

import br.com.primary.main.dao.jpa.Persistente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericJapDAO <T extends Persistente, E extends Serializable> {

    public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public void delete(T entity) throws DaoException;
	
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException;
	
	public T show(E id) throws MoreThanOneRegisterException, TableException, DaoException;
	
	public Collection<T> showAll() throws DaoException;
}
