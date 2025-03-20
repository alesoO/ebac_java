package br.com.primary.test.br.com.main.jpa;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Random;

import br.com.primary.main.dao.jpa.ClienteJpaDAO;
import br.com.primary.main.dao.jpa.IClienteJpaDAO;
import br.com.primary.main.dao.jpa.IProdutoJpaDAO;
import br.com.primary.main.dao.jpa.ProdutoJpaDAO;
import br.com.primary.main.domain.jpa.ClienteJpa;
import br.com.primary.main.domain.jpa.ProdutoJpa;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public class ProdutoJpaDAOTest {
    private IProdutoJpaDAO produtoDAO;

    public ProdutoJpaDAOTest() {
        this.produtoDAO = new ProdutoJpaDAO();
    }

    @After
    public void end() throws DaoException {
        Collection<ProdutoJpa> list = produtoDAO.showAll();
        list.forEach(cli -> {
            try {
                produtoDAO.delete(cli);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
    }
    @Test
    public void pesquisar() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ProdutoJpa produto = criarProduto("A1");
        Assert.assertNotNull(produto);
        ProdutoJpa produtoDB = this.produtoDAO.show(produto.getId());
        Assert.assertNotNull(produtoDB);
    }

    @Test
    public void salvar() throws TypeKeyNotFoundException, DaoException {
        ProdutoJpa produto = criarProduto("A2");
        Assert.assertNotNull(produto);
    }
    @Test
    public void excluir() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ProdutoJpa produto = criarProduto("A3");
        Assert.assertNotNull(produto);
        this.produtoDAO.delete(produto);
        ProdutoJpa produtoDB = this.produtoDAO.show(produto.getId());
        assertNull(produtoDB);
    }
    @Test
    public void alterarCliente() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        ProdutoJpa produto = criarProduto("A4");
        produto.setName("Rodrigo Pires");
        produtoDAO.edit(produto);
        ProdutoJpa produtoDB = this.produtoDAO.show(produto.getId());
        assertNotNull(produtoDB);
        Assert.assertEquals("Rodrigo Pires", produtoDB.getName());
    }
    @Test
    public void buscarTodos() throws TypeKeyNotFoundException, DaoException {
        criarProduto("A5");
        criarProduto("A6");
        Collection<ProdutoJpa> list = produtoDAO.showAll();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        for (ProdutoJpa prod : list) {
            this.produtoDAO.delete(prod);
        }

        list = produtoDAO.showAll();
        assertTrue(list != null);
        assertTrue(list.size() == 0);
    }

    private ProdutoJpa criarProduto(String code) throws TypeKeyNotFoundException, DaoException {
        ProdutoJpa produto = new ProdutoJpa();
        produto.setCode(code);
        produto.setDescription("Produto 1");
        produto.setName("Produto 1");
        produto.setValue(BigDecimal.TEN);
        produtoDAO.add(produto);
        return produto;
    }
}
