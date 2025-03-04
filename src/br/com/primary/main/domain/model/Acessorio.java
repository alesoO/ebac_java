package br.com.primary.main.domain.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data NoArgsConstructor
@Entity
@Table(name = "acessory")
public class Acessorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@ManyToMany(mappedBy = "acessorios", fetch = FetchType.LAZY)
	private List<Carro> carros = new ArrayList<>();
	
	public Acessorio(String nome) {
		this.nome = nome;
	}
	
	public Acessorio(String nome, Carro carro) {
		this.nome = nome;
		this.carros.add(carro);
	}
	
	public void addCarro(Carro carro) {
		this.carros.add(carro);
	}
	
	public void removeCarro(Carro carro) {
		this.carros.remove(carro);
	}
}
