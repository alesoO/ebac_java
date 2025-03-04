package br.com.primary.main.service;

import java.util.List;

public interface ICrud<T, ID, E> {
	List<T> findAll();
	T findById(ID id);
	T save(T entity);
	void delete(ID id);
	T add(ID id, E otherEntity);
	T remove(ID id, E otherEntity);
}
