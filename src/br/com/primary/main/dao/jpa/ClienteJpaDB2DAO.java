package br.com.primary.main.dao.jpa;

import br.com.primary.main.domain.jpa.ClienteJpa;

public class ClienteJpaDB2DAO extends GenericJpaDB2DAO<ClienteJpa, Long> implements IClienteJpaDAO<ClienteJpa> {
	public ClienteJpaDB2DAO() {
		super(ClienteJpa.class);
	}
}
