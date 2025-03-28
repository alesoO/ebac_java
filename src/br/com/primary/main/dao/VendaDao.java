package br.com.primary.main.dao;

import java.sql.SQLException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.primary.main.dao.factory.ProdutoQuantidadeFactory;
import br.com.primary.main.dao.factory.VendaFactory;
import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Cliente;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.domain.ProdutoQuantidade;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.Venda.Status;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class VendaDao extends GenericDao<Venda, String> implements IVendaDao {
	public VendaDao() {
		super(Venda.class);
	}

	@Override
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		super().edit(venda);
	}

	@Override
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		super().edit(venda);
	}

	@Override
	public void delete(String value) throws DaoException {
		throw new UnsupportedOperationException("Operação não permitida");
	}
	
	@Override
	public Venda add(Venda entity) throws TypeKeyNotFoundException, DaoException {

		try {
			entity.getProdutos().forEach(prod -> {
				Produto prodJpa = entityManager.merge(prod.getProduct());
				prod.setProduct(prodJpa);
			});
			Cliente cliente = entityManager.merge(entity.getCliente());
			entity.setCliente(cliente);
			entityManager.persist(entity);
			return entity;
		} catch (SQLException e) {
			throw new DaoException("ERRO SALVANDO VENDA ", e);
		}
	}

	@Override
	public Venda showWithCollection(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Venda> query = builder.createQuery(Venda.class);
		Root<Venda> root = query.from(Venda.class);
		root.fetch("cliente");
		root.fetch("produtos");
		query.select(root).where(builder.equal(root.get("id"), id));
		TypedQuery<Venda> tpQuery = entityManager.createQuery(query);
		Venda venda = tpQuery.getSingleResult();
		return venda;
	}
}
