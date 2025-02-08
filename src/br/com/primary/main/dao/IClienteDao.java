package br.com.primary.main.dao;

import br.com.primary.main.domain.Cliente;

public interface IClienteDao {
	public Integer add(Cliente cliente) throws Exception;
	public Cliente show(String code) throws Exception;
	public Integer delete(Cliente clienteBD) throws Exception;
}
