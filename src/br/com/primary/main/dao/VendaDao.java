package br.com.primary.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.primary.main.dao.factory.ProdutoQuantidadeFactory;
import br.com.primary.main.dao.factory.VendaFactory;
import br.com.primary.main.dao.generics.GenericDao;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.domain.ProdutoQuantidade;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.domain.Venda.Status;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class VendaDao extends GenericDao<Venda, String> implements IVendaDao {
	
	@Override
	public Class<Venda> getTypeClass() {
		return Venda.class;
	}
	
	@Override
	public void updateData(Venda entity, Venda entityAdded) {
		entityAdded.setCode(entity.getCode());
		entityAdded.setStatus(entity.getStatus());
	}
	
	@Override
	public void delete(String value) {
		throw new UnsupportedOperationException("Operação não permitida");
	}
	
	@Override
	public void finishVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String sql = "UPDATE Sale set sale_status = ? WHERE id = ?";
			connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CONCLUIDA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erro Atualizando Objeto ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public void cancelVenda(Venda venda) throws TypeKeyNotFoundException, DaoException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String sql = "UPDATE Sale set sale_status = ? WHERE id = ?";
			connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CANCELADA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erro Atualizando Objeto ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	@Override
	protected String getQueryInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Sale ");
		sb.append("(ID, code, id_client_fk, total_value, sale_date, sale_status)");
		sb.append("VALUES (nextval('sq_sale'),?,?,?,?,?)");
		return sb.toString();
	}
	
	protected void setParametersQueryInsert(PreparedStatement stmInsert, Venda entity) throws SQLException {
		stmInsert.setString(1, entity.getCode());
		stmInsert.setLong(2, entity.getCliente().getId());
		stmInsert.setBigDecimal(3, entity.getTotalValue());
		stmInsert.setTimestamp(4, Timestamp.from(entity.getSaleDate()));
		stmInsert.setString(5, entity.getStatus().name());
	}
	
	@Override
	protected String getQueryDelete() {
		throw new UnsupportedOperationException("Operação não Permitida.");
	}
	
	@Override
	protected void setParametersQueryDelete(PreparedStatement stmDelete, String value) throws SQLException {
		throw new UnsupportedOperationException("Operação não Permitida.");
	}
	
	@Override
	protected String getQueryUpdate() {
		throw new UnsupportedOperationException("Operação não Permitida.");
	}
	
	protected void setParametersQueryUpdate(PreparedStatement stmUpdate, Venda entity) throws SQLException {
		throw new UnsupportedOperationException("Operação não Permitida.");
	}
	
	@Override
	protected void setParametersQuerySelect(PreparedStatement stmSelect, String value) throws SQLException {
		stmSelect.setString(1, value);
	}

	@Override
	public Venda show(String value) throws MoreThanOneRegisterException, TableException, DaoException {
		StringBuilder sb = sqlBaseSelect();
		sb.append("WHERE V.code = ?");
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement(sb.toString());
			setParametersQuerySelect(stm, value);
			rs = stm.executeQuery();
			if (rs.next()) {
				Venda sale = VendaFactory.convert(rs);
				searchSaleProducts(connection, sale);
				return sale;
			}
		} catch (SQLException e) {
			throw new DaoException("Erro Atualizando Objeto ", e);
		} finally {
			closeConnection(connection, stm, rs);
		}
		return null;
	}
	
	private void searchSaleProducts(Connection connection, Venda sale) throws DaoException {
		PreparedStatement stmProd = null;
		ResultSet rsProd = null;
		try {
			StringBuilder sbProd = new StringBuilder();
			sbProd.append("SELECT PQ.id, PQ,quantity, PQ.total_value, ");
			sbProd.append("P.id as id_product, P.code, P.name, P.desciption, P.value");
			sbProd.append("FROM ProductQuantity PQ ");
			sbProd.append("INNER JOIN Product P ON P.id = PQ.id_product_fk ");
			sbProd.append("WHERE PQ.id_sale_fk = ? ");
			sbProd = connection.prepareStatement(sbProd.toString());
			sbProd.setLong(1, sale.getId());
			rsProd = stmProd.executeQuery();
			Set<ProdutoQuantidade> products = new HashSet<>();
			while(rsProd.next()) {
				ProdutoQuantidade prodQ = ProdutoQuantidadeFactory.convert(rsProd);
				products.add(prodQ);
			}
			sale.setProdutos(products);
			sale.recalculateTotalSellValue();
		} catch (SQLException e) {
			throw new DaoException("Erro Atualizando Objeto ", e);
		} finally {
			closeConnection(connection, stmProd, rsProd);
		}
	}
	
	@Override
	public Collection<Venda> showAll() throws DaoException {
		List<Venda> list = new ArrayList<>();
		StringBuilder sb = sqlBaseSelect();
		try {
			Connection connection = getConnection();
			PreparedStatement stm = connection.prepareStatement(sb.toString());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Venda sale = VendaFactory.convert(rs);
				searchSaleProducts(connection, sale);
				list.add(sale);
			}
		} catch (SQLException e) {
			throw new DaoException("Erro consultando Objeto ", e);
		}
		return list;
	}
	
	private StringBuilder sqlBaseSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT V.id AS id_sale, V.code V.total_value, V.status_sale, ");
		sb.append("C.id as id_client, C.name, C.CPF, C.tel, C.address, C.number, C.city, C.state, C.cep");
		sb.append("FROM Sale V ");
		sb.append("INNER JOIN Client C ON V.id_client_fk = C.id");
		return sb;
	}
	
	@Override
	public Boolean add(Venda entity) throws TypeKeyNotFoundException, DaoException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS);
			setParametersQueryInsert(stm, entity);
			int rowsAffected = stm.executeUpdate();
			
			if(rowsAffected > 0) {
				try(ResultSet rs = stm.getGeneratedKeys()) {
					if(rs.next()) {
						entity.setId(rs.getLong(1));
					}
				}
				for(ProdutoQuantidade prod : entity.getProdutos()) {
					stm = connection.prepareStatement(getQueryInsertProdQuant());
					setParametersQueryInsertProdQuant(stm, entity, prod);
					rowsAffected = stm.executeUpdate();
				}
				return true;
			}
		} catch (SQLException e) {
			throw new DaoException("Erro Atualizando Objeto ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
		return false;
	}
	private String getQueryInsertProdQuant() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Product_Quantity");
		sb.append("(id_product_fk, id_sale_fk, quantity, total_value)");
		sb.append("VALUES (?,?,?,?)");
		return sb.toString();
	}
	
	private void setParametersQueryInsertProdQuant(PreparedStatement stm, Venda sale, ProdutoQuantidade prod) throws SQLException {
		stm.setLong(1, prod.getProduct().getId());
		stm.setLong(2, sale.getId());
		stm.setInt(3, prod.getQuantity());
		stm.setBigDecimal(4, prod.getTotalValue());
	}
}
