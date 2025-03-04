package br.com.primary.main.service.impl;

import java.util.List;
import br.com.primary.main.domain.model.*;
import br.com.primary.main.domain.repository.CarroRepository;
import br.com.primary.main.domain.repository.MarcaRepository;
import br.com.primary.main.service.ICrud;
import br.com.primary.main.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

public class MarcaService implements ICrud<Marca, Long, Acessorio> {
private final MarcaRepository repository;
	
	public MarcaService(MarcaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<Marca> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Marca findById(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}
	
	@Override
	public Marca save(Marca marca) {
		return repository.save(marca);
	}
	
	@Override
	public void delete(Long id) {
		var marca = this.findById(id);
		repository.delete(marca);
	}
	
	@Override
	public Marca add(Long id, Carro carro) {
		var marca = this.findById(id);
		marca.addCarro(carro);
		return repository.save(marca);
	}
	
	@Override
	public Marca remove(Long id, Carro carro) {
		var marca = this.findById(id);
		marca.removeCarro(carro);
		return repository.save(marca);
	}
}
