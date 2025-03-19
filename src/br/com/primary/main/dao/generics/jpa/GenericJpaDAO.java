package br.com.primary.main.dao.generics.jpa;

import br.com.primary.main.domain.jpa.Persistente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class GenericJpaDAO <T extends Persistente, E extends Serializable> implements IGenericJapDAO<T,E> {
	private static final String PERSISTENCE_UNIT_NAME = "MariaDB1";
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    private Class<T> persistenceClass;
	private String persistenceUnitName;

    public GenericJpaDAO(Class<T> persistenceClass, String persistenceUnitName) {
        this.persistenceClass = persistenceClass;
		this.persistenceUnitName = persistenceUnitName;
    }

    @Override
	public T add(T entity) throws TypeKeyNotFoundException, DaoException {
		openConnection();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public void delete(T entity) throws DaoException {
		openConnection();
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		closeConnection();
	}

	@Override
	public T edit(T entity) throws  TypeKeyNotFoundException, DaoException {
		openConnection();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public T show(E valor) throws MoreThanOneRegisterException, TableException, DaoException {
		openConnection();
		T entity = entityManager.find(this.persistenceClass, valor);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public Collection<T> showAll() throws DaoException {
		openConnection();
		List<T> list = entityManager.createQuery(getSelectSql(), this.persistenceClass).getResultList();
		closeConnection();
		return list;
	}

	protected void openConnection() {
		entityManagerFactory = Persistence.createEntityManagerFactory("ExempleJPA");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	protected void closeConnection() {
		entityManager.close();
		entityManagerFactory.close();
	}

	private String getSelectSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT obj FROM ");
		sb.append(this.persistenceClass.getSimpleName());
		sb.append(" obj");
		return sb.toString();
	}

	private String getPersistenceUnitName() {
		if (persistenceUnitName != null && !"".equals(persistenceUnitName)) {
			return persistenceUnitName;
		} else {
			return PERSISTENCE_UNIT_NAME;
		}
	}
}
