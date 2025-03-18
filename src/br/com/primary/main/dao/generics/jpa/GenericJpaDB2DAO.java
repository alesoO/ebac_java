package br.com.primary.main.dao.generics.jpa;

import br.com.idias.dao.Jpa.Persistente;
import java.io.Serializable;

public abstract class GenericJpaDB2DAO <T extends Persistente, E extends Serializable> extends GenericJpaDAO<T,E> {
    public GenericJpaDB2DAO(Class<T> persistenceClass) {
        super(persistenceClass, "MariaDB2");
    }
}
