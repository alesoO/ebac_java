package br.com.primary.main.service.generic;

import java.io.Serializable;
import java.util.Collection;
import br.com.primary.main.dao.Persistent;
import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public abstract class GenericService<T extends Persistent, E extends Serializable> implements IGenericService<T,E> {
	protected IGenericDAO<T,E> dao;
	
	public GenericService(IGenericDAO<T,E> dao) {
		this.dao = dao;
	}
	
	@Override
	public Boolean add(T entity) throws TypeKeyNotFoundException {
		return this.dao.add(entity);
	}
	
	@Override
	public void delete(E value) {
		this.dao.delete(value);
	}
	
	@Override
	public void edit(T entity) throws TypeKeyNotFoundException {
		this.edit(entity);
	}
	
	@Override
	public T show(E value) {
		return this.show(value);
	}
	
	@Override
	public Collection<T> showAll() {
		return this.dao.showAll();
	}
}
