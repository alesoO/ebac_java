package br.com.primary.main.service;

import java.util.List;

import br.com.primary.main.domain.Cliente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.service.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {
	Cliente searchCPF(Long cpf) throws DaoException;
	List<Cliente> filtrarClientes(String query);
}
