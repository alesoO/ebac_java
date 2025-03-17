package br.com.primary.main.dao.jpa;

import br.com.primary.main.domain.jpa.ProdutoJpa;
import br.com.primary.main.dao.generics.jpa.GenericJpaDAO;

public class ProdutoJpaDAO extends GenericJpaDAO<ProdutoJpa, Long> implements IProdutoJpaDAO {
	public ProdutoJpaDAO() {
		super(ProdutoJpa.class, "MariaDB1");
	}
}
