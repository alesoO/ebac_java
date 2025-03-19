package br.com.primary.main.dao.jpa;

import br.com.primary.main.dao.generics.jpa.IGenericJapDAO;
import br.com.primary.main.domain.jpa.Persistente;
public interface IClienteJpaDAO<T extends Persistente> extends IGenericJapDAO<T, Long> {
	
}
