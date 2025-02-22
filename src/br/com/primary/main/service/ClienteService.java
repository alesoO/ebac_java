package br.com.primary.main.service;

import br.com.primary.main.dao.IClienteDao;
import br.com.primary.main.domain.Cliente;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.service.generic.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
	public ClienteService(IClienteDao clienteDAO) {
		super(clienteDAO);
	}
	
	@Override
	public Cliente searchCPF(Long cpf) throws DaoException {
		try {
			return this.dao.show(cpf);
		} catch(MoreThanOneRegisterException | TableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
