package br.com.primary.main.domain;

import br.com.primary.annotation.Table;
import br.com.primary.annotation.TableColumn;
import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;

@Table("Client")
public class Cliente implements Persistent {
	@TableColumn(dbName = "id", setJavaName = "setId")
	private Long id;
	@TableColumn(dbName = "name", setJavaName = "setName")
	private String name;
	@TableColumn(dbName = "cpf", setJavaName = "setCpf")
	private Long cpf;
	@TableColumn(dbName = "tel", setJavaName = "setTel")
	private Long tel;
	@TableColumn(dbName = "address", setJavaName = "setAddress")
	private String address;
	@TableColumn(dbName = "number", setJavaName = "setNumber")
	private Integer number;
	@TableColumn(dbName = "city", setJavaName = "setCity")
	private String city;
	@TableColumn(dbName = "state", setJavaName = "setEstate")
	private String state;
	@TableColumn(dbName = "cep", setJavaName = "setCep")
	private String cep;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public Long getTel() {
		return tel;
	}
	public void setTel(Long tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
