package br.com.primary.main.domain.jpa;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import br.com.primary.main.domain.Cliente;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.domain.ProdutoQuantidade;
import br.com.primary.main.domain.Venda.Status;

public class VendaJpa implements Persistente {
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
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sale_seq")
	@SequenceGenerator(name="sale_seq", sequenceName="sq_sale", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "id_client_fk",
            foreignKey = @ForeignKey(name = "fk_sale_client"),
            referencedColumnName = "id", nullable = false
    )
    private ClienteJpa cliente;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    private Set<ProdutoQuantidadeJpa> produtos;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "sale_date", nullable = false)
    private Instant saleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false)
    private Status status;
	
	public VendaJpa() {
		produtos = new HashSet<>();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ClienteJpa getCliente() {
		return cliente;
	}

	public void setCliente(ClienteJpa cliente) {
		this.cliente = cliente;
	}

	public Set<ProdutoQuantidadeJpa> getProdutos() {
		return produtos;
	}
	
	public void addProduto(ProdutoJpa produto, Integer quantity) {
		validateStatus();
		Optional<ProdutoQuantidadeJpa> op = produtos.stream().filter(filter -> filter.getProduct().getCode().equals(produto.getCode())).findAny();
		if(op.isPresent()) {
			ProdutoQuantidadeJpa productQtd = op.get();
			productQtd.add(quantity);
		} else {
			ProdutoQuantidadeJpa prod = new ProdutoQuantidadeJpa();
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
	
	public void removeProduto(ProdutoJpa produto, Integer quantity) {
		validateStatus();
		Optional<ProdutoQuantidadeJpa> op = produtos.stream().filter(filter -> filter.getProduct().getCode().equals(produto.getCode())).findAny();
		if(op.isPresent()) {
			ProdutoQuantidadeJpa productQtd = op.get();
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
		for (ProdutoQuantidadeJpa prod : this.produtos) {
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

	public void setProdutos(Set<ProdutoQuantidadeJpa> produtos) {
		this.produtos = produtos;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
}
