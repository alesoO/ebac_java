package br.com.primary.main.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data NoArgsConstructor
@Entity
@Table(name = "brand")
public class Marca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Carro> carros = new ArrayList<>();
	
	public Marca(String nome, Carro carro) {
		this.nome = nome;
		this.carros.add(carro);
	}
	
	public Marca(String nome) {
		this.nome = nome;
	}
	
	public List<Carro> listCarrosMarca() {
		return this.carros;
	}
	
	public void addCarro(Carro carro) {
		this.carros.add(carro);
	}
	
	public void removeCarro(Carro carro) {
		this.carros.remove(carro);
	}
}
