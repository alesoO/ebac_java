package br.com.app.dao;

import br.com.app.domain.Client;
import java.util.Collection;

public interface ClientDAO {
	public Boolean add(Client client);
	
	public void remove(Long cpf);
	
	public void edit(Client client);
	
	public Client show(Long cpf);
	
	public Collection<Client> showAll();
}
