package br.com.primary.main.dao.jpa;

import br.com.primary.main.domain.jpa.ProdutoJpa;
import br.com.primary.main.dao.generics.jpa.GenericJpaDB1DAO;

public class ProdutoJpaDAO extends GenericJpaDB1DAO<ProdutoJpa, Long> implements IProdutoJpaDAO {
	public ProdutoJpaDAO() {
		super(ProdutoJpa.class);
	}
}
