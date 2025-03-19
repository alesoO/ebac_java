package br.com.primary.main.domain.jpa;

import java.math.BigDecimal;

import javax.persistence.*;
@Entity
@Table(name = "Product")
public class ProdutoJpa implements Persistente {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod_seq")
	@SequenceGenerator(name="prod_seq", sequenceName="sq_product", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "code", nullable = false, length = 10, unique = true)
	private String code;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@Column(name = "description", nullable = false, length = 50)
	private String description;
	
	@Column(name = "value", nullable = false)
	private BigDecimal value;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
