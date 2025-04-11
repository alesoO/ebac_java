package br.com.primary.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.primary.domain.Produto;
import br.com.primary.domain.Produto.Status;

public interface IProdutoRepository extends MongoRepository<Produto, String> {
    Opitional<Produto> findByCodigo(String codigo);
    Page<Produto> findAllByStatus(Pageable pageable, Status status);
}
