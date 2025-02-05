package br.com.primary.main.service;

import br.com.primary.main.domain.Cliente;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public interface IClienteService {
	
	public Boolean add(Cliente cliente) throws TypeKeyNotFoundException;
	
	Cliente searchCPF(Long cpf);
	
	void delete(Long cpf);
	
	void edit(Cliente cliente) throws TypeKeyNotFoundException;
}
