package br.com.primary.main.service;

import java.util.List;

public interface IGenericCrudService <T, ID> {
    List<T> findAll();
    T findById(ID id);
    T create(T entity);
    boolean delete(ID id);
}
