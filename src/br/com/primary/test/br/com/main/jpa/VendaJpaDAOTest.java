package br.com.primary.test.br.com.main.jpa;

import br.com.primary.main.dao.jpa.ClienteJpaDAO;
import br.com.primary.main.dao.jpa.IClienteJpaDAO;
import br.com.primary.main.dao.jpa.IProdutoJpaDAO;
import br.com.primary.main.dao.jpa.IVendaJpaDAO;
import br.com.primary.main.dao.jpa.ProdutoJpaDAO;
import br.com.primary.main.dao.jpa.VendaJpaDAO;
import br.com.primary.main.domain.jpa.ClienteJpa;
import br.com.primary.main.domain.jpa.ProdutoJpa;
import br.com.primary.main.domain.jpa.VendaJpa;
import br.com.primary.main.domain.jpa.VendaJpa.Status;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeElementNotFoundException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;
import br.com.primary.test.br.com.main.dao.VendaExclusaoJpaDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

public class VendaJpaDAOTest {
    private IVendaJpaDAO vendaDao;
    private IVendaJpaDAO vendaExclusaoDao;
    private IClienteJpaDAO clienteDao;
    private IProdutoJpaDAO produtoDao;
    private Random rd;
    private ClienteJpa cliente;
    private ProdutoJpa produto;

    public VendaJpaDAOTest() {
        this.vendaDao = new VendaJpaDAO();
        vendaExclusaoDao = new VendaExclusaoJpaDAO();
        this.clienteDao = new ClienteJpaDAO();
        this.produtoDao = new ProdutoJpaDAO();
        rd = new Random();
    }
    @Before
    public void init() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        this.cliente = cadastrarCliente();
        this.produto = cadastrarProduto("A1", BigDecimal.TEN);
    }
    @After
    public void end() throws DaoException {
        excluirVendas();
        excluirProdutos();
        clienteDao.delete(this.cliente);
    }
    @Test
    public void pesquisar() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        VendaJpa venda = criarVenda("A1");
        VendaJpa retorno = vendaDao.add(venda.getId());
        assertNotNull(retorno);
        VendaJpa vendaConsultada = vendaDao.show(venda.getId());
        assertNotNull(vendaConsultada);
        assertEquals(venda.getCode(), vendaConsultada.getCode());
    }
    @Test
    public void salvar() throws TypeKeyNotFoundException, DaoException, MoreThanOneRegisterException, TableException {
        VendaJpa venda = criarVenda("A2");
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);

        assertTrue(venda.getTotalValue().equals(BigDecimal.valueOf(20)));
        assertTrue(venda.getStatus().equals(Status.INICIADA));

        VendaJpa vendaConsultada = vendaDao.show(venda.getId());
        assertTrue(vendaConsultada.getId() != null);
        assertEquals(venda.getCode(), vendaConsultada.getCode());
    }
    @Test
    public void cancelarVenda() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A3";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        retorno.setStatus(Status.CANCELADA);
        vendaDao.cancelVenda(venda);

        VendaJpa vendaConsultada = vendaDao.show(venda.getId());
        assertEquals(codigoVenda, vendaConsultada.getCode());
        assertEquals(Status.CANCELADA, vendaConsultada.getStatus());
    }
    @Test
    public void adicionarMaisProdutosDoMesmo() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A4";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        VendaJpa vendaConsulta = vendaDao.showWithCollection(venda.getId());
        vendaConsulta.addProduto(produto, 1);

        assertTrue(vendaConsulta.getQuantityTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsulta.getTotalValue().equals(valorTotal));
        assertTrue(vendaConsulta.getStatus().equals(Status.INICIADA));
    }
    @Test
    public void adicionarMaisProdutosDiferentes() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A5";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCode());

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        vendaConsultada.addProduto(prod, 1);

        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
    }
    @Test(expected = DaoException.class)
    public void salvarVendaMesmoCodigoExistente() throws TypeKeyNotFoundException, DaoException {
        VendaJpa venda = criarVenda("A6");
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);

        VendaJpa venda1 = criarVenda("A6");
        VendaJpa retorno1 = vendaDao.add(venda1);
        assertNull(retorno1);
        assertTrue(venda.getStatus().equals(Status.INICIADA));
    }
    @Test
    public void removerProduto() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A7";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCode());

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        vendaConsultada.addProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));

        vendaConsultada.removeProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
    }
    @Test
    public void removerApenasUmProduto() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A8";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCode());

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        vendaConsultada.addProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));

        vendaConsultada.removeProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 2);
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));
        assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
    }
    @Test
    public void removerTodosProdutos() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A9";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        ProdutoJpa prod = cadastrarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCode());

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        vendaConsultada.addProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getTotalValue().equals(valorTotal));

        vendaConsultada.removeProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantityTotalProdutos() == 0);
        assertTrue(vendaConsultada.getTotalValue().equals(BigDecimal.valueOf(0)));
        assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
    }
    @Test
    public void finalizarVenda() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A10";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        venda.setStatus(Status.CONCLUIDA);
        vendaDao.finishVenda(venda);

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        assertEquals(venda.getCode(), vendaConsultada.getCode());
        assertEquals(Status.CONCLUIDA, vendaConsultada.getStatus());
    }
    @Test(expected = UnsupportedOperationException.class)
    public void tentarAdicionarProdutosVendaFinalizada() throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        String codigoVenda = "A11";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.add(venda);
        assertNotNull(retorno);
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCode());

        venda.setStatus(Status.CONCLUIDA);
        vendaDao.finishVenda(venda);

        VendaJpa vendaConsultada = vendaDao.showWithCollection(venda.getId());
        assertEquals(venda.getCode(), vendaConsultada.getCode());
        assertEquals(Status.CONCLUIDA, vendaConsultada.getStatus());

        vendaConsultada.addProduto(this.produto, 1);
    }

    private void excluirVendas() throws DaoException {
        Collection<VendaJpa> list = this.vendaExclusaoDao.showAll();
        list.forEach(prod -> {
            try {
                this.vendaExclusaoDao.delete(prod);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });
    }

    private ProdutoJpa cadastrarProduto(String codigo, BigDecimal valor) throws TypeKeyNotFoundException, MoreThanOneRegisterException, TableException, DaoException {
        ProdutoJpa produto = new ProdutoJpa();
        produto.setCode(codigo);
        produto.setDescription("Produto 1");
        produto.setName("Produto 1");
        produto.setValue(valor);
        produtoDao.add(produto);
        return produto;
    }

    private ClienteJpa cadastrarCliente() throws TypeElementNotFoundException, DaoException {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setCpf(rd.nextLong());
        cliente.setName("Rodrigo");
        cliente.setCity("São Paulo");
        cliente.setAddress("End");
        cliente.setState("SP");
        cliente.setNumber(10);
        cliente.setTel(1199999999L);
        clienteDao.add(cliente);
        return cliente;
    }

    private VendaJpa criarVenda(String codigo) {
        VendaJpa venda = new VendaJpa();
        venda.setCode(codigo);
        venda.setSaleDate(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(Status.INICIADA);
        venda.addProduto(this.produto, 2);
        return venda;
    }
}
