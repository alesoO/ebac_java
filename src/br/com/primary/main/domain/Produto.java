package br.com.primary.main.domain;

import java.math.BigDecimal;

import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;

public class Produto implements Persistent {
	@TypeKey("getCodigo")
	private String code;
	private String name;
	private String description;
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
}
