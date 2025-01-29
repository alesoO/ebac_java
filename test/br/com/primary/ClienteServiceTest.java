package br.com.primary;

import br.com.primary.dao.ClienteDao;
import br.com.primary.dao.ClienteDaoMock;
import br.com.primary.dao.IClienteDao;
import br.com.primary.service.ClienteService;
import org.junit.Assert;
import org.junit.Test;

public class ClienteServiceTest {

	@Test
    public void salvarTest() {
        IClienteDao mockDao = new ClienteDaoMock();
        ClienteService service = new ClienteService(mockDao);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoSalvarTest() {
        IClienteDao mockDao = new ClienteDao();
        ClienteService service = new ClienteService(mockDao);
        String retorno = service.salvar();
        Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test
    public void buscarTest() {
    	IClienteDao mockDao = new ClienteDaoMock();
    	ClienteService service = new ClienteService(mockDao);
    	String retorno = service.buscar();
    	Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoBuscarTest() {
        IClienteDao mockDao = new ClienteDao();
        ClienteService service = new ClienteService(mockDao);
        String retorno = service.buscar();
        Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test
    public void AtualizarTest() {
    	IClienteDao mockDao = new ClienteDaoMock();
    	ClienteService service = new ClienteService(mockDao);
    	String retorno = service.atualizar();
    	Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoAtualizarTest() {
        IClienteDao mockDao = new ClienteDao();
        ClienteService service = new ClienteService(mockDao);
        String retorno = service.atualizar();
        Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test
    public void excluirTest() {
    	IClienteDao mockDao = new ClienteDaoMock();
    	ClienteService service = new ClienteService(mockDao);
    	String retorno = service.excluir();
    	Assert.assertEquals("Sucesso", retorno);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void esperadoErroNoExcluirTest() {
        IClienteDao mockDao = new ClienteDao();
        ClienteService service = new ClienteService(mockDao);
        String retorno = service.excluir();
        Assert.assertEquals("Sucesso", retorno);
    }
}
