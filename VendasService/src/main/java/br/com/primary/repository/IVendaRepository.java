package br.com.primary.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.primary.domain.Venda;

public interface IVendaRepository extends MongoRepository<Venda, String> {
    Optional<Venda> findByCodigo(String codigo);
}
