package br.com.primary.main.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table("Venda")
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
	@SequenceGenerator(name = "prod_seq", sequenceName = "sq_produto", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "code", nullable = false, unique = true)
	private String code;
	@ManyToOne
	@JoinColumn(name = "id_cliente_fk", foreignKey = @ForeignKey(name = "fk_venda_cliente"), referencedColumnName = "id", nullable = false)
	private Cliente cliente;
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProdutoQuantidade> produtos;
	@Column(name = "total_value", nullable = false)
	private BigDecimal totalValue;
	@Column(name = "sale_date", nullable = false)
	private Instant saleDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "sale_status", nullable = false)
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
