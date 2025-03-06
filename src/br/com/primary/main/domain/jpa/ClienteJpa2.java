package br.com.primary.main.domain.jpa;

import javax.persistence.*;

@Entity
@Table(name = "Client")
public class ClienteJpa2 implements Persistente {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="client_seq")
	@SequenceGenerator(name="client_seq", sequenceName="sq_client", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "Tel", nullable = false)
	private String tel;
	
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
	@Column(name = "number", nullable = false)
	private String number;
	
	@Column(name = "city", nullable = false, length = 100)
	private String city;
	
	@Column(name = "state", nullable = false, length = 50)
	private String state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
