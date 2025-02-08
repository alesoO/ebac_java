package br.com.primary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import br.com.primary.main.dao.ClienteDao;
import br.com.primary.main.dao.IClienteDao;
import br.com.primary.main.domain.Cliente;

public class ClienteTest {
	
	@Test
	public void addTets() throws Exception {
		IClienteDao dao = new ClienteDao();
		
		Cliente cliente = new Cliente();
		cliente.setCode("01");
		cliente.setName("Jorge");
		
		Integer qtd = dao.add(cliente);
		assertTrue(qtd == 1);
		
		Cliente clienteBD = dao.show(cliente.getCode());
		assertNotNull(clienteBD);
		assertNotNull(clienteBD.getId());
		assertEquals(cliente.getCode(), clienteBD.getCode());
		assertEquals(cliente.getName(), clienteBD.getName());
		
		Integer qtdDel = dao.delete(clienteBD);
		assertNotNull(qtdDel);
	}
}