package br.com.primary.main.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.com.primary.annotation.Table;
import br.com.primary.annotation.TableColumn;
import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;

@Table("Sale")
public class Venda implements Persistent {
	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;
		public static Status getByName(String value) {
			for (Status status : Status.values()) {
				if(status.name().equals(value)) {
					return status;
				}
			}
			return null;
		}
	}
	
	@TableColumn(dbName = "id", setJavaName = "setId")
	private Long id;
	@TypeKey("getCode")
	@TableColumn(dbName = "code", setJavaName = "setCode")
	private String code;
	@TableColumn(dbName = "id_client_fk", setJavaName = "setIdClientFk")
	private Cliente cliente;
	private Set<ProdutoQuantidade> produtos;
	@TableColumn(dbName = "total_value", setJavaName = "setTotalValue")
	private BigDecimal totalValue;
	@TableColumn(dbName = "sale_date", setJavaName = "setSaleDate")
	private Instant saleDate;
	@TableColumn(dbName = "sale_status", setJavaName = "setStatus")
	private Status status;
	
	public Venda() {
		produtos = new HashSet<>();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}
	
	public void addProduto(Produto produto, Integer quantity) {
		validateStatus();
		Optional<ProdutoQuantidade> op = produtos.stream().filter(filter -> filter.getProduct().getCode().equals(produto.getCode())).findAny();
		if(op.isPresent()) {
			ProdutoQuantidade productQtd = op.get();
			productQtd.add(quantity);
		} else {
			ProdutoQuantidade prod = new ProdutoQuantidade();
			prod.setProduct(produto);
			prod.add(quantity);
			produtos.add(prod);
		}
		recalculateTotalSellValue();
	}
	
	private void validateStatus() {
		if(this.status == Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}
	
	public void removeProduto(Produto produto, Integer quantity) {
		validateStatus();
		Optional<ProdutoQuantidade> op = produtos.stream().filter(filter -> filter.getProduct().getCode().equals(produto.getCode())).findAny();
		if(op.isPresent()) {
			ProdutoQuantidade productQtd = op.get();
			if (productQtd.getQuantity()>quantity) {
				productQtd.remove(quantity);
				recalculateTotalSellValue();
			} else {
				produtos.remove(op.get());
				recalculateTotalSellValue();
			}
		}
	}
	
	public void removeAllProdutos() {
		validateStatus();
		produtos.clear();
		totalValue = BigDecimal.ZERO;
	}
	
	public Integer getQuantityTotalProdutos() {
		int result = produtos.stream().reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantity(), Integer::sum);
		return result;
	}
	
	public void recalculateTotalSellValue() {
		validateStatus();
		BigDecimal totalValue = BigDecimal.ZERO;
		for (ProdutoQuantidade prod : this.produtos) {
			totalValue = totalValue.add(prod.getTotalValue());
		}
		this.totalValue = totalValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public Instant getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Instant saleDate) {
		this.saleDate = saleDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	
}
