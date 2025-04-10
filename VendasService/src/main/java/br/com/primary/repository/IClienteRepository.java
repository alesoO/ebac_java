package br.com.primary.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.primary.domain.Cliente;

public interface IClienteRepository extends MongoRepository<Cliente, String> {
    Opitional<Cliente> findByCpf(String cpf);
}
