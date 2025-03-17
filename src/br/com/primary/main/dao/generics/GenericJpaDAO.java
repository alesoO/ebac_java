package br.com.primary.main.dao.generics;

import java.io.Serializable;

import br.com.primary.main.dao.jpa.IGenericJapDAO;
import br.com.primary.main.domain.jpa.Persistente;

public class GenericJpaDAO <T extends Persistente, E extends Serializable> implements IGenericJapDAO<T, E> {
	private static final String PERSISTENCE_UNIT_NAME = "MariaDB1";
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	private Class<T> persistenceClass = persistenceClass;
	private String persistenceUnitName = persistenceUnitName;
}
