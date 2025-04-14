package br.com.primary.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.primary.domain.Produto;

@FeignClient(name = "produto", url = "${application.produtoService.endpointConsultarProduto}")
public interface IProdutoService {
    @RequestMapping(Value = "/{codigo}", produces = "application/json", headers = "application/json")
    Produto buscarProduto(@RequestParam("codigo") String codigoProduto);
}
