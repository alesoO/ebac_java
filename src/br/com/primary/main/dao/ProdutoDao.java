package br.com.primary.main.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Cliente;
import br.com.primary.main.domain.Produto;

public class ProdutoDao extends GenericDao<Produto, String> implements IProdutoDao {
	public ProdutoDao() {
		super(Produto.class);
	}

	@Override
	public List<Produto> filtrarProdutos(String query) {
		TypedQuery<Produto> tpQuery = this.entityManager.createNamedQuery("Produto.findByNome", this.persistenteClass);
		tpQuery.setParameter("nome", "%" + query + "%");
		return tpQuery.getResultList();
	}
}
