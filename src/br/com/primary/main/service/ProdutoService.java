package br.com.primary.main.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.primary.main.dao.IProdutoDao;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.service.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {
	
	private IProdutoDao produtoDao;

	@Inject
	public ProdutoService(IProdutoDao produtoDao) {
		super(produtoDao);
		this.produtoDao = produtoDao;
	}

	@Override
	public List<Produto> filtrarProdutos(String query) {
		return produtoDao.filtrarProdutos(query);
	}
}
