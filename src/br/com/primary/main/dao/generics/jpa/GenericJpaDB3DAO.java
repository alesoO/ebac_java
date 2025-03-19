package br.com.primary.main.dao.generics.jpa;

import br.com.primary.main.domain.jpa.Persistente;
import java.io.Serializable;

public abstract class GenericJpaDB3DAO <T extends Persistente, E extends Serializable> extends GenericJpaDAO<T,E> {
    public GenericJpaDB3DAO(Class<T> persistenceClass) {
        super(persistenceClass, "Mysql1");
    }
}
