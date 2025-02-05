package br.com.primary.main.service;

import br.com.primary.main.dao.IProdutoDao;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.service.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {
	
	public ProdutoService(IProdutoDao dao) {
		super(dao);
	}
}
