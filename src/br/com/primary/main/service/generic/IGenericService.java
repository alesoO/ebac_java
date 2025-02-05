package br.com.primary.main.service.generic;

import java.io.Serializable;
import java.util.Collection;

import br.com.primary.main.dao.Persistent;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IGenericService<T extends Persistent, E extends Serializable> {
	
	public Boolean add(T entity) throws TypeKeyNotFoundException;
	
	public void delete(E value);
	
	public void edit(T entity) throws TypeKeyNotFoundException;
	
	public T show(E value);
	
	public Collection<T> showAll();
}
