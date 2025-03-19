package br.com.primary.test.br.com.main.jpa;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.primary.main.dao.jpa.ClienteJpaDAO;
import br.com.primary.main.dao.jpa.ClienteJpaDB2DAO;
import br.com.primary.main.dao.jpa.IClienteJpaDAO;
import br.com.primary.main.domain.jpa.ClienteJpa;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class ClienteJpaDao2BancosTest {
    private IClienteJpaDAO<ClienteJpa> clienteDao;
    private IClienteJpaDAO<ClienteJpa> clienteDB2Dao;

    private Random rd;

    public ClienteJpaDao2BancosTest() {
        this.clienteDao = new ClienteJpaDAO();
        this.clienteDB2Dao = new ClienteJpaDB2DAO();
        rd = new Random();
    }

    @After
    public void end() throws DaoException {
        Collection<ClienteJpa> list1 = clienteDao.showAll();
        excluir1(list1);

        Collection<ClienteJpa> list2 = clienteDB2Dao.showAll();
        excluir2(list2);
    }
    private void excluir1(Collection<ClienteJpa> list) {
        list.forEach(cli -> {
            try {
                clienteDao.delete(cli);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void excluir2(Collection<ClienteJpa> list) {
        list.forEach(cli -> {
            try {
                clienteDB2Dao.delete(cli);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ClienteJpa cliente = criarCliente();
        clienteDao.add(cliente);

        ClienteJpa clienteConsultado = clienteDao.show(cliente.getId());
        Assert.assertNotNull(clienteConsultado);

        cliente.setId(null);
        clienteDB2Dao.add(cliente);

        ClienteJpa clienteConsultado2 = clienteDB2Dao.show(cliente.getId());
        Assert.assertNotNull(clienteConsultado2);
    }

    @Test
    public void salvarCliente() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = clienteDao.add(cliente);
        Assert.assertNotNull(retorno);

        ClienteJpa clienteConsultado = clienteDao.show(retorno.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.delete(cliente);

        ClienteJpa clienteConsultado1 = clienteDao.show(retorno.getId());
        Assert.assertNotNull(clienteConsultado1);
    }
    @Test
    public void excluirCliente() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = clienteDao.add(cliente);
        Assert.assertNotNull(retorno);

        ClienteJpa clienteConsultado = clienteDao.show(retorno.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.delete(cliente);
        clienteConsultado = clienteDao.show(cliente.getId());
        Assert.assertNotNull(clienteConsultado);
    }
    @Test
    public void alterarCliente() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = clienteDao.add(cliente);
        Assert.assertNotNull(retorno);

        ClienteJpa clienteConsultado = clienteDao.show(cliente.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteConsultado.setName("Rodrigo Pires");
        clienteDao.edit(clienteConsultado);

        ClienteJpa clienteAlterado = clienteDao.show(clienteConsultado.getId());
        Assert.assertNotNull(clienteAlterado);
        Assert.assertEquals("Rodrigo Pires", clienteAlterado.getName());

        clienteDao.delete(cliente);
        clienteConsultado = clienteDao.show(clienteAlterado.getId());
        Assert.assertNull(clienteAlterado);
    }
    @Test
    public void buscarTodos() throws TypeKeyNotFoundException, DaoException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = clienteDao.add(cliente);
        Assert.assertNotNull(retorno);

        ClienteJpa cliente1 = criarCliente();
        ClienteJpa retorno1 = clienteDao.add(cliente1);
        Assert.assertNotNull(retorno1);

        Collection<ClienteJpa> list = clienteDao.showAll();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        list.forEach(cli -> {
            try {
                clienteDao.delete(cli);
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        });
        Collection<ClienteJpa> list1 = clienteDao.showAll();
        assertTrue(list != null);
        assertTrue(list.size() == 0);
    }

    private ClienteJpa criarCliente() {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setCpf(rd.nextLong());
        cliente.setName("Rodrigo");
        cliente.setCity("São Paulo");
        cliente.setAddress("End");
        cliente.setState("SP");
        cliente.setNumber(10);
        cliente.setTel(1199999999L);
        return cliente;
    }
}
