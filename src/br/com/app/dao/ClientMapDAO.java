package br.com.app.dao;

import br.com.app.domain.Client;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientMapDAO implements ClientDAO {
	
	private Map<Long, Client> map;
	
	public ClientMapDAO() {
		this.map = new HashMap<>();
	}
	
	@Override
	public Boolean add(Client client) {
		if(this.map.containsKey(client.getCpf())) {
			return false;
		}
		this.map.put(client.getCpf(), client);
		return true;
	}

	@Override
	public void remove(Long cpf) {
		Client clientAdded = this.map.get(cpf);
		
		if(clientAdded != null) {
			this.map.remove(clientAdded.getCpf(), clientAdded);
		}
	}

	@Override
	public void edit(Client client) {
		Client clientAdded = this.map.get(client.getCpf());
		if(clientAdded != null) {
			clientAdded.setName(client.getName());
			clientAdded.setTel(client.getTel());
			clientAdded.setTel(client.getTel());
			clientAdded.setNumber(client.getNumber());
			clientAdded.setAddress(client.getAddress());
			clientAdded.setCity(client.getCity());
			clientAdded.setEstate(client.getEstate());
		}
	}

	@Override
	public Client show(Long cpf) {
		return this.map.get(cpf);
	}

	@Override
	public Collection<Client> showAll() {
		return this.map.values();
	}
	
}
