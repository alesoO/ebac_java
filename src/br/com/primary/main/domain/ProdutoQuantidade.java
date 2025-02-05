package br.com.primary.main.domain;

import java.math.BigDecimal;

public class ProdutoQuantidade {
	private Produto product;
	private Integer quantity;
	private BigDecimal totalValue;
	
	public ProdutoQuantidade() {
		this.quantity = 0;
		this.totalValue = BigDecimal.ZERO;
	}
	public Produto getProduct() {
		return product;
	}
	public void setProduct(Produto product) {
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
