package br.com.primary.main.dao.jpa;

import java.io.Serializable;
import java.util.Collection;

import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IClienteJpaDAO<C> extends IGenericJapDAO<ClienteJpa, Long> {
	
}
