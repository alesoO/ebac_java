package br.com.primary.main.dao;

import java.util.List;

import br.com.primary.main.dao.generics.IGenericDAO;
import br.com.primary.main.domain.Produto;

public interface IProdutoDao extends IGenericDAO<Produto, String> {
    List<Produto> filtrarProdutos(String query);
}
