package br.com.primary.main.dao.jpa;

import br.com.primary.main.dao.generics.jpa.GenericJpaDB1DAO;
import br.com.primary.main.domain.jpa.ClienteJpa;

public class ClienteJpaDAO extends GenericJpaDB1DAO<ClienteJpa, Long> implements IClienteJpaDAO<ClienteJpa> {
	public ClienteJpaDAO() {
		super(ClienteJpa.class);
	}
}
