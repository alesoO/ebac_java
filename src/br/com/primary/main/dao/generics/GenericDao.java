package br.com.primary.main.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.primary.annotation.Table;
import br.com.primary.annotation.TableColumn;
import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;
import br.com.primary.main.dao.generics.jdbc.ConnectionFactory;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeElementNotFoundException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public abstract class GenericDao<T extends Persistent, E extends Serializable> implements IGenericDAO<T, E> {
	protected Class<T> persistenteClass;
	@PersistenteContext
	protected  EntityManager entityManager;

	public GenericDao(Class<T> persistenteClass) {
		this.persistenteClass = persistenteClass;
	}

	@Override
	public T add(T entity) throws TypeKeyNotFoundException, DaoException {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public void delete(T entity) throws DaoException {
		if (entityManager.contains(entity)) {
			entityManager.remove(entity);
		} else {
			T managerCustomer = entityManager.find(this.persistenteClass, entity.getId());
			if(managerCustomer != null) {
				entityManager.remove(managerCustomer);
			}
		}
	}

	@Override
	public T edit(T entity) throws TypeKeyNotFoundException, DaoException {
		entity = entityManager.merge(entity);
		return entity;
	}

	@Override
	public T show(E valor) throws MoreThanOneRegisterException, TableException, DaoException {
		T entity = entityManager.find(this.persistenteClass, valor);
		return entity;
	}

	@Override
	public Collection<T> showAll() throws DaoException {
		List<T> list = entityManager.createQuery(getSelectSql(), this.persistenteClass).getResultList();
		return list;
	}

	private String getSelectSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT obj From ");
		sb.append(this.persistenteClass.getSimpleName());
		sb.append(" obj");
		return sb.toString();
	}
}
