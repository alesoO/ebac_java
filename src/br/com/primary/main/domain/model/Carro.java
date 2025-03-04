package br.com.primary.main.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
@Entity
@Table(name = "cars")
public class Carro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String modelo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "marca_id")
	private Marca marca;
	
	@ManyToMany
	@JoinTable(name = "carro_acessorio",
	joinColumns = @JoinColumn(name = "carro_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "acessorio_id", referencedColumnName = "id"))
	private List<Acessorio> acessorios = new ArrayList<>();
	
	public Carro(String modelo, Marca marca, Acessorio acessorio) {
		this.modelo = modelo;
		this.marca = marca;
		this.acessorios.add(acessorio);
	}
	
	public Carro(String modelo, Marca marca) {
		this.modelo = modelo;
		this.marca = marca;
	}
	
	public void addAcessorio(Acessorio acessorio) {
		this.acessorios.add(acessorio);
	}
	
	public void removeAcessorio(Acessorio acessorio) {
		this.acessorios.remove(acessorio);
	}
}
