package br.com.primary.main.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ProdutoQuantidade")
public class ProdutoQuantidade {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_qtd_seq")
	@SequenceGenerator(name = "prod_qtd_seq", sequenceName = "sq_prod_qtd", initialValue = 1, allocationSize = 1)
	private Long id;
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Produto produto;
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	@Column(name = "total_value", nullable = false)
	private BigDecimal totalValue;
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "id_sale_fk",
	foreignKey = @ForeignKey(name = "fk_prod_qtd_sale"),
	referencedColumnName = "id", nullable = false)
	private Venda venda;

	public ProdutoQuantidade() {
		this.quantity = 0;
		this.totalValue = BigDecimal.ZERO;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
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
	public Venda getVenda() {
        return venda;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
    }
	
	public void add(Integer quantity) {
		this.quantity += quantity;
		BigDecimal newValue = this.produto.getValue().multiply(BigDecimal.valueOf(quantity));
		BigDecimal newTotal = this.totalValue.add(newValue);
		this.totalValue = newTotal;
	}
	
	public void remove(Integer quantity) {
		this.quantity -= quantity;
		BigDecimal newValue = this.produto.getValue().multiply(BigDecimal.valueOf(quantity));
		this.totalValue = this.totalValue.subtract(newValue);
	}

}
