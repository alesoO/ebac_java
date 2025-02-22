package br.com.primary.main.domain;

import java.math.BigDecimal;

import br.com.primary.annotation.Table;
import br.com.primary.annotation.TableColumn;
import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;

@Table("Product")
public class Produto implements Persistent {
	@TableColumn(dbName = "id", setJavaName = "setId")
	private Long id;
	@TypeKey("getCode")
	@TableColumn(dbName = "code", setJavaName = "setCode")
	private String code;
	@TableColumn(dbName = "name", setJavaName = "setName")
	private String name;
	@TableColumn(dbName = "description", setJavaName = "setDescription")
	private String description;
	@TableColumn(dbName = "value", setJavaName = "setValue")
	private BigDecimal value;
	@TableColumn(dbName = "category", setJavaName = "setCategory")
	private String category;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
