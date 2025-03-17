package br.com.primary.main.dao.jpa;

import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.jpa.*;
import br.com.primary.main.dao.generics.jpa.*;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class VendaJpaDAO extends GenericJpaDAO<VendaJpa, Long> implements IVendaJpaDAO {
	public VendaJpaDAO() {
		super(VendaJpa.class, "MariaDB1");
	}
	@Override
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		super.edit(venda);
	}
	@Override
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		super.edit(venda);
	}
	@Override
	public void delete(VendaJpa entity) throws DaoException {
		throw new UnsupportedOperationException("Operação não permitida");
	}
	@Override
	public VendaJpa add(VendaJpa entity) throws TypeKeyNotFoundException, DaoException {
		try {
			openConnection();
			entity.getProdutos().forEach(prod -> {
				ProdutoJpa prodJpa = entityManager.merge(prod.getProduct());
				prod.setProduct(prodJpa);
			});
			ClienteJpa cliente = entityManager.merge(entity.getCliente());
			entity.setCliente(cliente);
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			closeConnection();
			return entity;
		} catch (Exception e) {
			throw new DaoException("Erro Salvando Venda", e);
		}
	}
	
	public VendaJpa showWithCollection(Long id) {
		openConnection();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaBuilder<VendaJpa> query = builder.createQuery(VendaJpa.class);
		Root<VendaJpa> root = query.from(VendaJpa.class);
		root.fetch("cliente");
		root.fetch("produtos");
		query.select(root).where(builder.equal(root.get("id"), id));
		TypedQuery<VendaJpa> tpQuery = entityManager.createQuery(query);
		VendaJpa venda = tpQuery.getSingleResult();
		closeConnection();
		return venda;
	}
}
