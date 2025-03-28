package br.com.primary.main.service;

import java.util.List;

import br.com.primary.main.domain.Produto;
import br.com.primary.main.service.generic.IGenericService;

public interface IProdutoService extends IGenericService<Produto, String> {
    List<Produto> filtrarProdutos(String query);
}
