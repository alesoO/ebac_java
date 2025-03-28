package br.com.primary.main.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.primary.main.domain.produto;
import br.com.primary.main.domain.Produto;
import br.com.primary.main.service.IProdutoService;
import br.com.primary.main.utils.ReplaceUtils;

public class ProdutoController implements Serializable {
    private static final long serialVersionUID = 367088063926303823L;
    private Produto produto;
    private Collection<Produto> produtos;

    @Inject
    private IProdutoService produtoService;
    private Boolean isUpdate;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.produto = new Produto();
            this.produtos = produtoService.showAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar os produto"));
        }
    }
    public void cancel() {
        try {
            this.isUpdate = false;
            this.produto = new Produto();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
        }
    }
    public void edit(Produto produto) {
        try {
            this.isUpdate = false;
            this.produto = produto;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o produto"));
		}
    }
    public void delete(Produto produto) {
        try {
            produtoService.delete(produto);
            produtos.remove(produto);    
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o produto"));
        }
    }
    public void add() {
        try {
            produtoService.add(produto);
            this.produtos = produtoService.showAll();
            this.produto = new Produto();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar criar o produto"));
        }
    }

    public void update() {
        try {
            produtoService.edit(this.produto);
            cancel();
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("produto Atualiado com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar o produto"));
        }
    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public produto getProduto() {
        return produto;
    }

    public void setProduto(produto produto) {
        this.produto = produto;
    }
    public Collection<Produto> getProduto() {
        return produtos;
    }
    public void setProduto(Collection<Produto> produtos) {
        this.produtos = produtos;
    }
    public Boolean getIsUpdate() {
        return isUpdate;
    }
    public void setIsUpdate(Boolean isUpadte) {
        this.isUpdate = isUpadte;
    }
}
