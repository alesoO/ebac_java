package br.com.primary.main.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import br.com.primary.main.domain.Produto;
import br.com.primary.main.domain.ProdutoQuantidade;
import br.com.primary.main.domain.Venda;
import br.com.primary.main.service.IClienteService;
import br.com.primary.main.service.IProdutoService;

@Named
@ViewScoped
public class VendaController implements Serializable {
    private static final long serialVersionUID = -3508753726177740824L;
    private Venda venda;
    private Collection<Venda> vendas;
    @Inject
    private IVendaService vendaService;
    @Inject 
    private IClienteService clienteService;
    @Inject
    private IProdutoService produtoService;
    private Boolean isUpdate;
    private LocalDate dataVenda;
    private Integer quantidadeProduto;
    private Set<ProdutoQuantidade> produtos;
    private Produto produtoSelecionado;
    private BigDecimal valorTotal;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.venda = new Venda();
            this.produtos = new HashSet<>();
            this.vendas = vendaService.showAll();
            this.valorTotal = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar as vendas"));
        }
    }
    public void cancel() {
        try {
            this.isUpdate = false;
            this.venda = new Venda();
            this.produtos = new HashSet<>();
            this.valorTotal = BigDecimal.ZERO;
            this.dataVenda = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
        }
    }
    public void edit(Venda venda) {
        try {
            this.isUpdate = true;
            this.venda = this.vendaService.showWithCollection(venda.getId());
            this.dataVenda = LocalDate.ofInstant(this.venda.getSaleDate(), ZoneId.systemDefault());
            this.produtos = this.venda.getProdutos();
            this.venda.recalculateTotalSellValue();
            this.valorTotal = this.venda.getTotalValue();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar a venda"));
        }
    }
    public void delete(Venda venda) {
        try {
            vendaService.finishVenda(venda);
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar a venda"));
        }
    }
    public void finalizar(Venda venda) {
        try {
            vendaService.finishSale(venda);
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar finalizar a venda"));
        }
    }
    public void add() {
        try {
            venda.setSaleDate(dataVenda.atStartOfDay(ZoneId.systemDefault()).toInstant());
            vendaService.add(venda);
            this.vendas = vendaService.showAll();
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cadastrar a venda"));
        }
    }
    public void update() {
        try {
            vendaService.edit(this.venda);
            this.vendas = vendaService.showAll();
            cancel();
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Venda atualiada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar a venda"));
		}
    }
    public void adicionarProduto() {
        Optional<ProdutoQuantidade> prodOp = this.venda.getProdutos().stream().filter(prodF -> prodF.getProduct().getCode().equals(this.produtoSelecionado.getCode())).findFirst();
        if (prodOp.isPresent()) {
            ProdutoQuantidade prod = prodOp.get();
            prod.add(this.quantidadeProduto);
        } else {
            ProdutoQuantidade prod = new ProdutoQuantidade();
            prod.setProduct(this.produtoSelecionado);
            prod.add(this.quantidadeProduto);
            prod.setVenda(this.venda);
            this.venda.getProdutos().add(prod);
        }
        this.venda.recalculateTotalSellValue();
        this.produtos = this.venda.getProdutos();
        this.valorTotal = this.venda.getTotalValue();
    }
    public void removerProduto() {
        Optional<ProdutoQuantidade> prodOp = this.venda.getProdutos().stream().filter(prodF -> prodF.getProduct().getCode().equals(this.produtoSelecionado.getCode())).findFirst();
        if (prodOp.isPresent()) {
            ProdutoQuantidade prod = prodOp.get();
            prod.remove(this.quantidadeProduto);
            if (prod.getQuantity() == 0 || prod.getQuantity() < 0) {
                this.venda.getProdutos().remove(prod);
            }
            this.venda.recalculateTotalSellValue();
            this.produtos = this.venda.getProdutos();
            this.valorTotal = this.venda.getTotalValue();
        }
    }
    public void removerProduto(ProdutoQuantidade produto) {
        this.venda.getProdutos().remove(produto);
        this.venda.recalculateTotalSellValue();
        this.produtos = this.venda.getProdutos();
        this.valorTotal = this.venda.getTotalValue();
    }
    public void onRowEdit(RowEditEvent<ProdutoQuantidade> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void adicionarOuRemoverProduto(ProdutoQuantidade prod) {
        if(prod.getQuantity() != this.quantidadeProduto) {
            int quantidade = this.quantidadeProduto - prod.getQuantity();
            if(quantidade > 0) {
                prod.add(quantidade);
            } else {
                this.produtos.forEach(pro -> {
                    this.valorTotal = this.valorTotal.add(pro.getTotalValue());
                });
            }
        }
    }
    public List<Cliente> filtrarClientes(String query) {
        return this.clienteService.filtrarClientes(query);
    }
    public List<Cliente> filtrarProdutos(String query) {
        return this.clienteService.filtrarProdutos(query);
    }
    public String voltarTelaInicial() {
        return "/index.xhtml";
    }
    public Venda getVenda() {
        return venda;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    public Collection<Venda> getVendas() {
        return vendas;
    }
    public void setVendas(Collection<Venda> vendas) {
        this.vendas = vendas;
    }
    public Boolean getIsUpdate() {
        return isUpdate;
    }
    public void setIsUpdate() {
        this.isUpdate = isUpdate;
    }
    public LocalDate getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
    public Set<ProdutoQuantidade> getProdutos() {
        return produtos;
    }
    public void setProdutos(Set<ProdutoQuantidade> produtos) {
        this.produtos = produtos;
    }
    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }
    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }
    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }
    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
