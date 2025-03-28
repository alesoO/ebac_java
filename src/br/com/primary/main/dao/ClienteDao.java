package br.com.primary.main.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.TypedQuery;

import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Cliente;

public class ClienteDao extends GenericDao<Cliente, Long> implements IClienteDao {
	
	public ClienteDao() {
		super(Cliente.class);
	}

	@Override
	public List<Cliente> filtrarClientes(String query) {
		TypedQuery<Cliente> tpQuery = this.entityManager.createNamedQuery("Cliente.findByNome", this.persistenteClass);
		tpQuery.setParameter("nome", "%" + query + "%");
		return tpQuery.getResultList();
	}
	
}
