package br.com.primary.main.service;

import br.com.primary.main.domain.Cliente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.service.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {
	Cliente searchCPF(Long cpf) throws DaoException;
}
