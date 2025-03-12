package br.com.primary.main.domain.jpa;

import java.math.BigDecimal;

import br.com.primary.main.domain.Produto;

@Entity
@Table(name = "product_quantity")
public class ProdutoQuantidadeJpa {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod_qtd_seq")
	@SequenceGenerator(name="prod_qtd_seq", sequenceName="sq_prod_qtd", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ProdutoJpa product;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "totalValue", nullable = false)
	private BigDecimal totalValue;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_sale_fk",
			foreignKye = @ForeignKey(name = "fk_prod_qtd_sale"),
			referencedColumnName = "id", nullable = false
	)
	private VendaJpa sale;
	
	public ProdutoQuantidadeJpa() {
		this.quantity = 0;
		this.totalValue = BigDecimal.ZERO;
	}
	public ProdutoJpa getProduct() {
		return product;
	}
	public void setProduct(ProdutoJpa product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void add(Integer quantity) {
		this.quantity += quantity;
		BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
		BigDecimal newTotal = this.totalValue.add(newValue);
		this.totalValue = newTotal;
	}
	
	public void remove(Integer quantity) {
		this.quantity -= quantity;
		BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
		this.totalValue = this.totalValue.subtract(newValue);
	}
}
